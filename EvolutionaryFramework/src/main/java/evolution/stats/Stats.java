package evolution.stats;

import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public abstract class Stats {
    protected final String algorithmName;
    protected final int popSize;
    protected final int numberOfBars;
    protected final int maxNumberOfNotes;
    protected final String representationType;
    protected final List<String> chordProgression;
    protected final Pair<String, String> melodyKey;
    protected final HashMap<String, Double> weights;
    protected final double crossoverProbability;
    protected final List<Pair<String, Double>> crossoverType;
    protected final double mutationProbability;
    protected final List<Pair<String, Double>> mutationType;
    protected final String selectionType;
    protected final String matingPoolSelectionType;
    protected final int numberOfGenerations;
    protected final List<String> criteria;
    protected final String folderName;


    public Stats(String algorithmName, int popSize, int numberOfBars, int maxNumberOfNotes,
                 String representationType, List<String> chordProgression, Pair<String, String> melodyKey,
                 HashMap<String, Double> weights, double crossoverProbability, List<Pair<String, Double>> crossoverType,
                 double mutationProbability, List<Pair<String, Double>> mutationType, String selectionType,
                 String matingPoolSelectionType, int numberOfGenerations, List<String> criteria, String folderName) {
        this.algorithmName = algorithmName;
        this.popSize = popSize;
        this.representationType = representationType;
        this.weights = weights;
        this.crossoverProbability = crossoverProbability;
        this.crossoverType = crossoverType;
        this.mutationProbability = mutationProbability;
        this.mutationType = mutationType;
        this.selectionType = selectionType;
        this.matingPoolSelectionType = matingPoolSelectionType;
        this.numberOfGenerations = numberOfGenerations;
        this.criteria = criteria;
        this.numberOfBars = numberOfBars;
        this.maxNumberOfNotes = maxNumberOfNotes;
        this.chordProgression = chordProgression;
        this.melodyKey = melodyKey;
        this.folderName = folderName;
    }

    public abstract void generateJSON(int numberOfIteration);

    public abstract void updateStats(int generationNumber, List<Individual> population);

    public static void writeJSONToFile(JSONObject iterationJSON, int numberOfIteration, String folderName) {
        try (FileWriter file = new FileWriter("results/" + folderName + "/result" + "_" + numberOfIteration + ".json")) {
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
            System.out.println("Directory was created!");

        } catch (IOException e) {
            System.err.println("Failed: directory wasn't created!" + e.getMessage());
        }
    }

}
