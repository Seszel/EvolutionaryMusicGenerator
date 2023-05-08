package evolution.stats;

import evolution.solution.Individual;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class StatsNSGA_II extends Stats {

    private final HashMap<Integer, List<Individual>> frontsForGeneration;

    public StatsNSGA_II(String algorithmName, int popSize,
                        int numberOfBars, int maxNumberOfNotes, String representationType,
                        List<String> chordProgression, Pair<String, String> melodyKey,
                        String crossoverType, Pair<String, Double> mutationType, String selectionType, String matingPoolSelectionType,
                        int numberOfGenerations, List<String> criteria, String folderName) {
        super(algorithmName, popSize, numberOfBars, maxNumberOfNotes, representationType, chordProgression, melodyKey,
                crossoverType, mutationType, selectionType, matingPoolSelectionType, numberOfGenerations, criteria, folderName);
        this.frontsForGeneration = new HashMap<>();
    }

    @Override
    public void updateStats(int generationNumber, List<Individual> population) {
        List<Individual> populationToJSON = new ArrayList<>();
        for (Individual i : population) {
//            if (i.getFrontRank() > 10){break;}
            Individual newI = new Individual(i.getGenome(), i.getFitness(), i.getFrontRank());
            populationToJSON.add(newI);
        }
        frontsForGeneration.put(generationNumber, populationToJSON);
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
        metaParameters.put("crossoverType", crossoverType);
        metaParameters.put("mutationType", mutationType.getLeft());
        metaParameters.put("mutationProbability", mutationType.getRight());
        metaParameters.put("selectionType", selectionType);
        metaParameters.put("matingPoolSelectionType", matingPoolSelectionType);
        metaParameters.put("numberOfGenerations", numberOfGenerations);
        metaParameters.put("criteria", criteria);

        structure.put("metaParameters", metaParameters);


        JSONObject generationList;
        JSONObject frontsList;
        JSONArray frontIndividuals;
        JSONObject individualDetails;
        JSONObject fitnessDetails;


        HashMap<String, Double> fitness;
        List<Individual> generation;
        Individual individual;



        generationList = new JSONObject();

        for (Integer generationKey : frontsForGeneration.keySet()){
            frontsList = new JSONObject();
            frontIndividuals = new JSONArray();

            generation = frontsForGeneration.get(generationKey);

            int rank = 1;

            for (int i = 0; i < generation.size(); i++) {
                individual = generation.get(i);

                if (individual.getFrontRank() != rank) {

                    frontsList.put("front_" + rank, frontIndividuals);
                    rank = individual.getFrontRank();
                    frontIndividuals = new JSONArray();

                    if (i == generation.size() - 1) {
                        individualDetails = new JSONObject();

                        individualDetails.put("melody", individual.getGenome().getMelodyJFugue());
                        fitnessDetails = new JSONObject();
                        fitness = individual.getFitness();
                        for (String criterion : criteria) {
                            fitnessDetails.put(criterion, fitness.get(criterion));
                        }
                        individualDetails.put("fitness", fitnessDetails);

                        frontIndividuals.add(individualDetails);

                        frontsList.put("front_" + rank, frontIndividuals);
                        break;
                    }
                }

                individualDetails = new JSONObject();

                individualDetails.put("melody", individual.getGenome().getMelodyJFugue());
                fitnessDetails = new JSONObject();
                fitness = individual.getFitness();
                for (String criterion : criteria) {
                    fitnessDetails.put(criterion, fitness.get(criterion));
                }
                individualDetails.put("fitness", fitnessDetails);

                frontIndividuals.add(individualDetails);

                if (i == generation.size() - 1) {
                    frontsList.put("front_" + rank, frontIndividuals);
                }

//                if (rank > 3){
//                    break;
//                }

            }
            generationList.put("generation_" + generationKey, frontsList);
        }

        structure.put("experiment", generationList);

        writeJSONToFile(structure, numberOfIteration, folderName);

    }


}


