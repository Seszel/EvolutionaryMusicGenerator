package evolution.stats;

import evolution.solution.Individual;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsNSGA_II_oneCriterion extends Stats {

    private final HashMap<Integer, List<Individual>> populationForGeneration;

    public StatsNSGA_II_oneCriterion(String algorithmName, int popSize,
                                     int numberOfBars, int maxNumberOfNotes, String representationType,
                                     List<String> chordProgression, Pair<String, String> melodyKey,
                                     double crossoverProbability, List<Pair<String, Double>> crossoverType,
                                     double mutationProbability, List<Pair<String, Double>> mutationType,
                                     String selectionType, String matingPoolSelectionType,
                                     int numberOfGenerations, List<String> criteria, String folderName) {
        super(algorithmName, popSize, numberOfBars, maxNumberOfNotes, representationType, chordProgression, melodyKey,
                crossoverProbability, crossoverType, mutationProbability, mutationType, selectionType, matingPoolSelectionType, numberOfGenerations, criteria, folderName);
        this.populationForGeneration = new HashMap<>();
    }

    @Override
    public void updateStats(int generationNumber, List<Individual> population) {
        List<Individual> populationToJSON = new ArrayList<>();
        for (Individual i : population) {
            Individual newI = new Individual(i.getGenome(), i.getFitness());
            populationToJSON.add(newI);
        }
        populationForGeneration.put(generationNumber, populationToJSON);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void generateJSON(int numberOfIteration) {

        JSONObject structure = new JSONObject();

        JSONObject metaParameters = new JSONObject();

        metaParameters.put("algorithmName", algorithmName);
        metaParameters.put("populationSize", popSize);
        metaParameters.put("numberOfBars", numberOfBars);
        metaParameters.put("maxNumberOfNotes", maxNumberOfNotes);
        metaParameters.put("representationType", representationType);
        metaParameters.put("chordProgression", StringUtils.join(chordProgression, ", "));
        metaParameters.put("melodyKeyValue", melodyKey.getLeft());
        metaParameters.put("melodyKeyType", melodyKey.getRight());

        metaParameters.put("crossoverProbability", crossoverProbability);
        JSONObject crossoverObject = new JSONObject();
        for (Pair<String, Double> stringDoublePair : crossoverType){
            crossoverObject.put(stringDoublePair.getLeft(), stringDoublePair.getRight());
        }
        metaParameters.put("crossoverType", crossoverObject);

        metaParameters.put("mutationProbability", mutationProbability);
        JSONObject mutationObject = new JSONObject();
        for (Pair<String, Double> stringDoublePair : mutationType) {
            mutationObject.put(stringDoublePair.getLeft(), stringDoublePair.getRight());
        }
        metaParameters.put("mutationType", mutationObject);

        metaParameters.put("selectionType", selectionType);
        metaParameters.put("matingPoolSelectionType", matingPoolSelectionType);
        metaParameters.put("numberOfGenerations", numberOfGenerations);
        metaParameters.put("criteria", criteria);

        structure.put("metaParameters", metaParameters);

        JSONObject generationList = new JSONObject();
        JSONArray populationIndividuals;
        JSONObject individualDetails;
        JSONObject fitnessDetails;
        JSONObject generationObject;

        HashMap<String, Double> fitness;

        for (Integer generationKey : populationForGeneration.keySet()) {
            populationIndividuals = new JSONArray();
            generationObject = new JSONObject();
            int i = 0;
            for (Individual individual : populationForGeneration.get(generationKey)) {
                individualDetails = new JSONObject();

                individualDetails.put("melody", individual.getGenome().getMelodyJFugue());
                fitnessDetails = new JSONObject();
                fitness = individual.getFitness();
                for (String criterion : criteria) {
                    fitnessDetails.put(criterion, fitness.get(criterion));
                }
                individualDetails.put("fitness", fitnessDetails);
                if (i == 0) {
                    generationObject.put("bestIndividual", individualDetails);
                }
                populationIndividuals.add(individualDetails);
                i++;

            }
//            generationObject.put("population", populationIndividuals);
            generationList.put("generation_" + generationKey, generationObject);

        }


        structure.put("experiment", generationList);

        writeJSONToFile(structure, numberOfIteration, folderName);
    }
}
