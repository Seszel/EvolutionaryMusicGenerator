package evolution.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import evolution.solution.Individual;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Util {
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static <T> ArrayList<T> flattenListOfListsStream(ArrayList<ArrayList<T>> list) {
        return (ArrayList<T>) list.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static void generateJSONFile(ArrayList<Individual> population, int numberOfGeneration, int numberOfIteration){
        JSONArray populationList = new JSONArray();
        JSONObject frontsList = new JSONObject();
        JSONArray frontIndividuals = new JSONArray();
        JSONObject individualDetails;
        int rank = 1;

        for (int i=0; i<population.size(); i++) {
            if (population.get(i).getFrontRank() != rank) {
                frontsList.put("front_" + rank, frontIndividuals);
                populationList.add(frontsList);
                rank = population.get(i).getFrontRank();
                frontsList = new JSONObject();
                frontIndividuals = new JSONArray();
                if (i == population.size()-1){
                    individualDetails = new JSONObject();
                    individualDetails.put("genome" ,population.get(i).getGenome().getMelodyJFugue());
                    individualDetails.put("fitness", population.get(i).getFitness());

                    frontIndividuals.add(individualDetails);
                    frontsList.put("front_" + rank, frontIndividuals);
                    populationList.add(frontsList);
                    break;
                }
            }

            individualDetails = new JSONObject();
            individualDetails.put("genome" ,population.get(i).getGenome().getMelodyJFugue());
            individualDetails.put("fitness", population.get(i).getFitness());

            frontIndividuals.add(individualDetails);

            if (i == population.size()-1){
                frontsList.put("front_" + rank, frontIndividuals);
                populationList.add(frontsList);
            }

        }
        try (FileWriter file = new FileWriter("results/population" + "_" + numberOfIteration + "_" + numberOfGeneration + ".json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(populationList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
