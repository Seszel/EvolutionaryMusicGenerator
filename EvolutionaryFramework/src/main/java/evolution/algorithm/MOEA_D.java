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
import evolution.stats.StatsMOEA_D;
import lombok.var;
import me.tongfei.progressbar.ProgressBar;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.ChordProgression;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MOEA_D extends AEvolutionaryAlgorithm {
    private final int numberOfNeighbours;

    public MOEA_D(int popSize, int numberOfBars, int maxNumberOfNotes,
                  String representationType, List<String> chordProgression,
                  Pair<String, String> melodyKey, String crossoverType,
                  Pair<String, Double> mutationType, String selectionType,
                  String matingPoolSelectionType, int numberOfGenerations, int numberOfIteration,
                  List<String> criteria, Pair<Boolean, Integer> saveToJSON, String folderName,
                  int numberOfNeighbours) {
        super(popSize, numberOfBars, maxNumberOfNotes, representationType,
                chordProgression, melodyKey, crossoverType, mutationType,
                selectionType, matingPoolSelectionType, numberOfGenerations, numberOfIteration, criteria, saveToJSON, folderName);

        this.numberOfNeighbours = numberOfNeighbours;
    }

    @Override
    public void run() {

        StatsMOEA_D stats = new StatsMOEA_D("MOEA/D", popSize,
                numberOfBars, maxNumberOfNotes, representationType,
                chordProgression, melodyKey, crossoverType,
                mutationType, selectionType, matingPoolSelectionType,
                numberOfGenerations, criteria, folderName, numberOfNeighbours);

        System.out.println("Algorithm MOEA/D is working, iteration: " + (numberOfIteration + 1));

        var params = new EvaluationParameters("JoannaParameters");
        params.addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION_PATTERN,
                        Representation.ChordProgression(melodyKey.getRight()))
                .addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION,
                        chordProgression)
                .addParam(EvaluationParameters.ParamName.MELODY_KEY,
                        melodyKey);
        ImmutableList<Integer> representation = Representation.getReprInt(representationType);

        PopulationMOEA_D population = new PopulationMOEA_D(
                popSize, representationType, criteria,
                numberOfBars, maxNumberOfNotes,
                chordProgression, melodyKey, params
        );

        population.setExternalPopulation();
        population.setWeightVectors();
        population.setEuclideanDistancesWeightVectors();
        population.setNeighbours(numberOfNeighbours);
        population.generatePopulation(representation);
        population.setReferencePointsZ();

        List<Integer> generations = IntStream.rangeClosed(1, numberOfGenerations)
                .boxed().collect(Collectors.toList());

        for (Integer g : ProgressBar.wrap(generations, "Iteration: " + (numberOfIteration + 1))) {
            for (int p = 0; p < popSize; p++) {

                Pair<Individual, Individual> parentsIndexes = MatingPoolSelection.randomFromNeighbourhood(numberOfNeighbours, population, p);
                Pair<Genome, Genome> parents = new MutablePair<>(parentsIndexes.getLeft().getGenome(), parentsIndexes.getRight().getGenome());
                Random random = new Random();
                Individual offspring;

                Pair<Genome, Genome> offsprings = Crossover.crossover(getCrossoverType(), parents);
                if (random.nextDouble() <= 0.5) {
                    assert offsprings != null;
                    offspring = new Individual(
                            Mutation.mutation(getMutationType(),
                                    offsprings.getLeft(), representation));
                } else {
                    assert offsprings != null;
                    offspring = new Individual(
                            Mutation.mutation(getMutationType(),
                                    offsprings.getRight(), representation));
                }
                offspring.getGenome().setMelodyJFugue(maxNumberOfNotes);
                offspring.setFitness(this.criteria, params);
                population.updateReferencePointsZ(offspring);
                population.updateNeighboursSolutions(p, offspring);
                population.updateExternalPopulation(offspring);
            }
            if (saveToJSON.getLeft() && (g % saveToJSON.getRight() == 1 || g == numberOfGenerations)) {
                stats.updateStats(g, population.getExternalPopulation());
            }
        }

        if (saveToJSON.getLeft()) {
            stats.generateJSON(numberOfIteration);
        }


        for (Individual individual : population.getExternalPopulation()) {
            Player player = new Player();

            Pattern chords = new ChordProgression(chordProgression.toString())
                    .setKey("C")
                    .allChordsAs("$0w $1w $2w $3w")
                    .getPattern()
                    .setVoice(0)
                    .setInstrument("Piano")
                    .setTempo(90);

            Pattern pattern = new Pattern("X[Volume]=13000" + individual.getGenome().getMelodyJFugue())
                    .setTempo(90)
                    .setInstrument("Piano")
                    .setVoice(1);
            player.play(chords, pattern);
        }


        System.out.println("MOEA/D ended his work, iteration: " + (numberOfIteration + 1));
    }


}

