package evolution.stats;

import evolution.solution.Individual;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsMOEA_D extends Stats {

    private final int numberOfNeighbours;
    private final HashMap<Integer, List<Individual>> externalPopulationForGeneration = new HashMap<>();

    public StatsMOEA_D(String algorithmName, int popSize,
                       int numberOfBars, int maxNumberOfNotes,
                       String representationType, List<String> chordProgression, Pair<String, String> melodyKey, HashMap<String, Double> weights,
                       double crossoverProbability, List<Pair<String, Double>> crossoverType,
                       double mutationProbability, List<Pair<String, Double>> mutationType,
                       String selectionType, String matingPoolSelectionType,
                       int numberOfGenerations, List<String> criteria, String folderName,
                       int numberOfNeighbours) {
        super(algorithmName, popSize, numberOfBars, maxNumberOfNotes, representationType, chordProgression, melodyKey, weights, crossoverProbability, crossoverType, mutationProbability, mutationType, selectionType, matingPoolSelectionType, numberOfGenerations, criteria, folderName);
        this.numberOfNeighbours = numberOfNeighbours;
    }

    @Override
    public void updateStats(int generationNumber, List<Individual> externalPopulation) {
        List<Individual> populationToJSON = new ArrayList<>();
        for (Individual i : externalPopulation) {
            Individual newI = new Individual(i.getGenome(), i.getFitness(), i.getPenalty());
            populationToJSON.add(newI);
        }
        externalPopulationForGeneration.put(generationNumber, populationToJSON);
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

        JSONObject weightsObject = new JSONObject();
        weightsObject.putAll(weights);
        metaParameters.put("weightsValues", weightsObject);

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
        metaParameters.put("numberOfNeighbours", numberOfNeighbours);
        metaParameters.put("criteria", criteria);

        structure.put("metaParameters", metaParameters);

        JSONObject generationList = new JSONObject();
        JSONArray externalPopulationIndividuals;
        JSONObject individualDetails;
        JSONObject fitnessDetails;

        HashMap<String, Double> fitness;

        for (Integer generationKey : externalPopulationForGeneration.keySet()){
            externalPopulationIndividuals = new JSONArray();
            for (Individual individual : externalPopulationForGeneration.get(generationKey)){
                individualDetails = new JSONObject();

                individualDetails.put("melody", individual.getGenome().getMelodyJFugue());
                fitnessDetails = new JSONObject();
                fitness = individual.getFitness();
                for (String criterion : criteria) {
                    fitnessDetails.put(criterion, fitness.get(criterion));
                }
                individualDetails.put("fitness", fitnessDetails);
                individualDetails.put("penalty", individual.getPenalty());

                externalPopulationIndividuals.add(individualDetails);
            }
            generationList.put("generation_" + generationKey, externalPopulationIndividuals);
        }

        structure.put("experiment", generationList);

        writeJSONToFile(structure, numberOfIteration, folderName);

    }

}
