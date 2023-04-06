package evolution.algorithm;

import com.google.common.collect.ImmutableList;
import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.operator.MatingPoolSelection;
import evolution.population.PopulationNSGA_II;
import evolution.solution.Individual;
import evolution.stats.StatsNSGA_II;
import evolution.util.Util;
import lombok.var;
import org.apache.commons.lang3.tuple.Pair;
import org.jfugue.player.Player;

import java.util.ArrayList;
import java.util.List;

public class NSGA_II extends AEvolutionaryAlgorithm {

    public NSGA_II(int popSize, int numberOfBars, int maxNumberOfNotes,
                   String representationType, List<String> chordProgression,
                   Pair<String, String> melodyKey, String crossoverType, Pair<String, Double> mutationType,
                   String selectionType, String matingPoolSelectionType,
                   int numberOfGenerations, int numberOfIteration, List<String> criteria,
                   Pair<Boolean, Integer> saveToJSON, String folderName) {

        super(popSize, numberOfBars, maxNumberOfNotes,
                representationType, chordProgression, melodyKey,
                crossoverType, mutationType, selectionType,
                matingPoolSelectionType, numberOfGenerations, numberOfIteration, criteria, saveToJSON, folderName);
    }

    @Override
    public void run() {

        StatsNSGA_II stats = new StatsNSGA_II("NSGA-II", popSize,
                numberOfBars, maxNumberOfNotes, representationType,
                chordProgression, melodyKey, crossoverType,
                mutationType, selectionType, matingPoolSelectionType,
                numberOfGenerations, criteria, folderName);

        System.out.println("Algorithm NSGA_II is working, iteration: " + (numberOfIteration + 1));

        var params = new EvaluationParameters("JoannaParameters");
        params.addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION_PATTERN,
                        Representation.ChordProgression(melodyKey.getRight()))
                .addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION,
                        chordProgression)
                .addParam(EvaluationParameters.ParamName.MELODY_KEY,
                        melodyKey);
        PopulationNSGA_II population = new PopulationNSGA_II(
                popSize, representationType, criteria,
                numberOfBars, maxNumberOfNotes,
                chordProgression, melodyKey, params
        );
        ImmutableList<Integer> representation = Representation.getReprInt(representationType);
        Player player = new Player();

        population.generatePopulation(representation);
        population.generateFronts();

        for (int g = 1; g <= numberOfGenerations; g++) {

            var matingPool = MatingPoolSelection.tournament(
                    10, popSize, population
            );
            population.createOffsprings(matingPool, representation, getCrossoverType(), getMutationType());

            population.changePopulation();

            population.generateFronts();

            List<List<Individual>> newPopulation = new ArrayList<>();
            int newPopulationSize = 0;

            for (List<Individual> front : population.getFronts()) {
                population.crowdingDistanceAssignment(front);
                newPopulation.add(front);
                if ((front.size() + newPopulationSize) > popSize) {
                    break;
                }
                newPopulationSize += front.size();
            }

            population.crowdedComparisonOperator(newPopulation);
            population.setPopulation(
                    new ArrayList<>(Util.flattenListOfListsStream(newPopulation).subList(0, popSize))
            );

            if (saveToJSON.getLeft() && (g % saveToJSON.getRight() == 0 || g == numberOfGenerations)) {
                stats.updateStats(g, population.getPopulation());
            }
        }

        if (saveToJSON.getLeft()) {
            stats.generateJSON(numberOfIteration);
        }
//        for (Individual individual : population.getPopulation()) {
//            Pattern pattern = new Pattern();
//            pattern.setTempo(90);
//            pattern.add(individual.getGenome().getMelodyJFugue());
//            player.play(pattern);
//        }

        System.out.println("Nsga_II algorithm ended work, iteration: " + (numberOfIteration + 1));
    }

}
