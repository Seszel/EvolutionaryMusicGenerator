package evolution.algorithm;

import com.google.common.collect.ImmutableList;
import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.operator.MatingPoolSelection;
import evolution.population.PopulationNSGA_II;
import evolution.solution.Individual;
import evolution.util.Util;
import lombok.var;
import org.apache.commons.lang3.tuple.Pair;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NSGA_II extends AEvolutionaryAlgorithm {

    public NSGA_II(int popSize, int numberOfBars, int maxNumberOfNotes,
                   String representationType, List<String> chordProgression,
                   Pair<String, String> melodyKey, String crossoverType, Pair<String, Double> mutationType,
                   String selectionType, String matingPoolSelectionType,
                   int numberOfGenerations, int numberOfIteration, List<String> criteria, boolean saveToJSON) {

        super(popSize, numberOfBars, maxNumberOfNotes,
                representationType, chordProgression, melodyKey,
                crossoverType, mutationType, selectionType,
                matingPoolSelectionType, numberOfGenerations, numberOfIteration, criteria, saveToJSON);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {

        System.out.println("Algorithm NSGA_II is working");

        var stats = new EvaluationParameters("JoannaParameters");
        stats.addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION_PATTERN,
                        Representation.ChordProgression(melodyKey.getRight()))
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


        JSONObject iterationJSONObject = new JSONObject();
        JSONObject algorithmJSONObject = new JSONObject();

        for (int n = 0; n < numberOfGenerations; n++) {

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
            if (n%100 == 0){
                iterationJSONObject.put("generation_" + (n + 1), Util.generateJSONObject(population.getPopulation(), criteria));
            }
        }
        algorithmJSONObject.put("NSGA-II", iterationJSONObject);
        Util.writeJSONFile(algorithmJSONObject, numberOfIteration, folderName);

        for (Individual individual : population.getPopulation()) {
            Pattern pattern = new Pattern();
            pattern.setTempo(90);
            pattern.add(individual.getGenome().getMelodyJFugue());
            player.play(pattern);
        }

        System.out.println("Nsga_II algorithm ended work, iteration:" + (numberOfIteration + 1));
    }

    @Override
    public void writeToJSON() {

    }

}
