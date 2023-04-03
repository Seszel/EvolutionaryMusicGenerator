package evolution.algorithm;

import com.google.common.collect.ImmutableList;
import evolution.music.Genome;
import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.operator.Crossover;
import evolution.operator.MatingPoolSelection;
import evolution.operator.Mutation;
import evolution.population.PopulationMOEA_D;
import evolution.solution.Individual;
import lombok.var;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jfugue.player.Player;

import java.util.List;
import java.util.SplittableRandom;

public class MOEA_D extends AEvolutionaryAlgorithm {
    private final int numberOfNeighbours;

    public MOEA_D(int popSize, int numberOfBars, int maxNumberOfNotes,
                  String representationType, List<String> chordProgression,
                  Pair<String, String> melodyKey, String crossoverType,
                  Pair<String, Double> mutationType, String selectionType, String matingPoolSelectionType,
                  int numberOfGenerations, int numberOfIteration, List<String> criteria, boolean saveToJSON,
                  int numberOfNeighbours) {
        super(popSize, numberOfBars, maxNumberOfNotes, representationType,
                chordProgression, melodyKey, crossoverType, mutationType,
                selectionType, matingPoolSelectionType, numberOfGenerations, numberOfIteration, criteria, saveToJSON);

        this.numberOfNeighbours = numberOfNeighbours;
    }

    @Override
    public void run() {

        System.out.println("Algorithm MOEA/D is working");

        var stats = new EvaluationParameters("JoannaParameters");
        stats.addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION_PATTERN,
                        Representation.ChordProgression(melodyKey.getRight()))
                .addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION,
                        chordProgression)
                .addParam(EvaluationParameters.ParamName.MELODY_KEY,
                        melodyKey);
        ImmutableList<Integer> representation = Representation.getReprInt(representationType);
        Player player = new Player();

        PopulationMOEA_D population = new PopulationMOEA_D(
                popSize, representationType, criteria,
                numberOfBars, maxNumberOfNotes,
                chordProgression, melodyKey, stats
        );

        population.setExternalPopulation();
        population.setWeightVectors();
        population.setEuclideanDistancesWeightVectors();
        population.setNeighbours(numberOfNeighbours);
        population.generatePopulation(representation);
        population.setReferencePointsZ();


        for (int g = 0; g < numberOfGenerations; g++) {
            for (int p = 0; p < popSize; p++) {

                Pair<Individual, Individual> parentsIndexes = MatingPoolSelection.randomFromNeighbourhood(numberOfNeighbours, population, p);
                Pair<Genome, Genome> parents = new MutablePair<>(parentsIndexes.getLeft().getGenome(), parentsIndexes.getRight().getGenome());
                SplittableRandom random = new SplittableRandom();
                Individual offspring;

                Pair<Genome, Genome> offsprings = Crossover.crossover(getCrossoverType(), parents);
                if (random.nextInt(1, 101) <= 50) {
                    assert offsprings != null;
                    offspring = new Individual(
                            Mutation.mutation(getMutationType(),
                                    offsprings.getLeft(), representation));
                } else {
                    offspring = new Individual(
                            Mutation.mutation(getMutationType(),
                                    offsprings.getRight(), representation));
                }
                offspring.getGenome().setMelodyJFugue(maxNumberOfNotes);
                offspring.setFitness(this.criteria, stats);
                population.updateReferencePointsZ(offspring);
                population.updateNeighboursSolutions(p, offspring);
                population.updateExternalPopulation(offspring);
            }
        }


//        for (Individual individual : population.getExternalPopulation()) {
//            Pattern pattern = new Pattern();
//            pattern.setTempo(90);
//            pattern.add(individual.getGenome().getMelodyJFugue());
//            player.play(pattern);
//        }


        System.out.println("MOEA/D ended his work!" + (numberOfIteration + 1));
    }

    @Override
    public void writeToJSON() {

    }


}

