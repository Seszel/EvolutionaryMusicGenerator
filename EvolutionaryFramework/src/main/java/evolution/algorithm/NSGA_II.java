package evolution.algorithm;

import com.google.common.collect.ImmutableList;
import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.operator.MatingPoolSelection;
import evolution.population.PopulationNSGA_II;
import evolution.solution.Individual;
import evolution.util.Util;
import lombok.var;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jfugue.player.Player;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NSGA_II extends AEvolutionaryAlgorithm {

    public NSGA_II(int popSize, int numberOfBars, int maxNumberOfNotes,
                   String representationType, List<String> chordProgression,
                   String melodyKey, String crossoverType, String mutationType,
                   String selectionType, String matingPoolSelectionType,
                   int numberOfGenerations, int numberOfIterations, List<String> criteria) {

        super(popSize, numberOfBars, maxNumberOfNotes,
                representationType, chordProgression, melodyKey,
                crossoverType, mutationType, selectionType,
                matingPoolSelectionType, numberOfGenerations,
                numberOfIterations, criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {

        var stats = new EvaluationParameters("JoannaParameters");
                stats.addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION_PATTERN,
                        Representation.ChordProgressionMajor)
                .addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION,
                        chordProgression)
                .addParam(EvaluationParameters.ParamName.MELODY_KEY,
                        melodyKey);
        PopulationNSGA_II population = new PopulationNSGA_II(
                popSize, representationType, criteria,
                numberOfBars, maxNumberOfNotes,
                chordProgression, melodyKey, stats
        );
        ImmutableList<Integer> representation = Representation.getReprInt(representationType);
        Player player = new Player();

        population.generatePopulation(representation);
        population.generateFronts();

//        for (Individual individual : population.getPopulation()) {
//            player.play(individual.getGenome().getMelodyJFugue());
//            break;
//        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH:mm:ss");
        String folderName = dtf.format(now);
        Util.createDirectory(folderName);


        for (int i = 0; i < numberOfIterations; i++) {
            JSONObject iterationJSONObject = new JSONObject();
            System.out.println("Iteration number " + (i + 1));
            JSONObject algorithmJSONObject = new JSONObject();
            for (int n = 0; n < numberOfGenerations; n++) {

                var matingPool = MatingPoolSelection.tournament(
                        10, popSize, population
                );
                population.createOffsprings(matingPool, representation);

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

                iterationJSONObject.put("generation_" + (n + 1), Util.generateJSONObject(population.getPopulation(), criteria));
            }
            algorithmJSONObject.put("NSGA-II", iterationJSONObject);
            Util.writeJSONFile(algorithmJSONObject, i, folderName);
        }
        for (Individual individual : population.getPopulation()) {
            player.play(individual.getGenome().getMelodyJFugue());
        }
        System.out.println("Nsga_II algorithm ended work!");
    }

}
