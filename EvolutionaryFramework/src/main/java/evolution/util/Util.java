package evolution.util;

import evolution.solution.Individual;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Util {



    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static <T> ArrayList<T> flattenListOfListsStream(ArrayList<ArrayList<T>> list) {
        return (ArrayList<T>) list.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static JSONObject generateJSONObject(ArrayList<Individual> population, List<String> criteria) {

        JSONObject frontsList = new JSONObject();
        JSONArray frontIndividuals = new JSONArray();
        JSONObject individualDetails;
        JSONObject fitness;
        int rank = 1;

        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).getFrontRank() != rank) {
                frontsList.put("front_" + rank, frontIndividuals);
                rank = population.get(i).getFrontRank();
                frontIndividuals = new JSONArray();
                if (i == population.size() - 1) {
                    individualDetails = new JSONObject();
                    individualDetails.put("genome", population.get(i).getGenome().getMelodyJFugue());
                    fitness = new JSONObject();
                    for (int j = 0; j < criteria.size(); j++) {
                        fitness.put(criteria.get(j), population.get(i).getFitness().get(j));
                    }
                    individualDetails.put("fitness", fitness);

                    frontIndividuals.add(individualDetails);
                    frontsList.put("front_" + rank, frontIndividuals);
                    break;
                }
            }

            individualDetails = new JSONObject();
            individualDetails.put("genome", population.get(i).getGenome().getMelodyJFugue());
            fitness = new JSONObject();
            for (int j = 0; j < criteria.size(); j++) {
                fitness.put(criteria.get(j), population.get(i).getFitness().get(j));
            }
            individualDetails.put("fitness", fitness);

            frontIndividuals.add(individualDetails);

            if (i == population.size() - 1) {
                frontsList.put("front_" + rank, frontIndividuals);
            }

        }
        return frontsList;

    }

    public static void writeJSONFile(JSONObject iterationJSON, int numberOfIteration, String folderName) {
        try (FileWriter file = new FileWriter("results/" + folderName + "/result" + "_" + numberOfIteration + ".json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(iterationJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createDirectory(String folderName) {
        try {

            Path path = Paths.get("results/" + folderName);

            Files.createDirectories(path);

            System.out.println("Directory is created!");

        } catch (IOException e) {

            System.err.println("Failed to create directory!" + e.getMessage());

        }
    }

}
