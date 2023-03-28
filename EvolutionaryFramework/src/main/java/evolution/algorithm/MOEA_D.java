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
                  String melodyKey, String crossoverType,
                  String mutationType, String selectionType, String matingPoolSelectionType,
                  int numberOfGenerations, int numberOfIterations, List<String> criteria,
                  int numberOfNeighbours) {
        super(popSize, numberOfBars, maxNumberOfNotes, representationType,
                chordProgression, melodyKey, crossoverType, mutationType,
                selectionType, matingPoolSelectionType, numberOfGenerations,
                numberOfIterations, criteria);

        this.numberOfNeighbours = numberOfNeighbours;
    }

    @Override
    public void run() {

        var stats = new EvaluationParameters("JoannaParameters");
        stats.addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION_PATTERN,
                        Representation.ChordProgressionMajor)
                .addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION,
                        chordProgression)
                .addParam(EvaluationParameters.ParamName.MELODY_KEY,
                        melodyKey);
        ImmutableList<Integer> representation = Representation.getReprInt(representationType);
        Player player = new Player();

//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH:mm:ss");
//        String folderName = dtf.format(now);
//        Util.createDirectory(folderName);

        for (int i = 0; i < numberOfIterations; i++) {
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


            System.out.println("Iteration number " + (i + 1));
            for (int g = 0; g < numberOfGenerations; g++) {
                for (int p = 0; p < popSize; p++) {

                    Pair<Individual, Individual> parentsIndexes = MatingPoolSelection.randomFromNeighbourhood(numberOfNeighbours, population, p);
                    Pair<Genome, Genome> parents = new MutablePair<>(parentsIndexes.getLeft().getGenome(), parentsIndexes.getRight().getGenome());
                    SplittableRandom random = new SplittableRandom();
                    Individual offspring;

                    Pair<Genome, Genome> offsprings = Crossover.onePointCrossover(parents);
                    if (random.nextInt(1, 101) <= 50) {
                        offspring = new Individual(
                                Mutation.simpleMutation(
                                        offsprings.getLeft(), representation, numberOfBars, maxNumberOfNotes
                                ));
                    } else {
                        offspring = new Individual(
                                Mutation.simpleMutation(
                                        offsprings.getRight(), representation, numberOfBars, maxNumberOfNotes
                                ));
                    }
                    offspring.getGenome().setMelodyJFugue(maxNumberOfNotes);
                    offspring.setFitness(this.criteria, stats);
                    population.updateNeighboursSolutions(p, offspring);
                    population.updateExternalPopulation(offspring);
                }
            }

            for (Individual individual : population.getExternalPopulation()) {
                player.play(individual.getGenome().getMelodyJFugue());
            }
        }


        System.out.println("MOEA/D ended his work!");
    }


}

