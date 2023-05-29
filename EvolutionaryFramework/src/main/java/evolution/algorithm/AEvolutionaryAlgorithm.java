package evolution.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

abstract class AEvolutionaryAlgorithm implements Runnable {
    protected final int popSize;
    protected final int numberOfBars;
    protected final int maxNumberOfNotes;
    protected final String representationType;
    protected final List<String> chordProgression;
    protected final Pair<String, String> melodyKey;
    protected double crossoverProbability;
    protected final List<Pair<String, Double>> crossoverType;
    protected double mutationProbability;
    protected final List<Pair<String, Double>> mutationType;
    protected final String selectionType;
    protected final String matingPoolSelectionType;
    protected final int numberOfGenerations;
    protected final int numberOfIteration;
    protected final List<String> criteria;
    protected final HashMap<String,Pair<Double, Double>> criteriaRanges;
    protected final Pair<Boolean, Integer> saveToJSON;
    protected final String folderName;
    protected final boolean play;

    protected AEvolutionaryAlgorithm(int popSize, int numberOfBars, int maxNumberOfNotes,
                                     String representationType, List<String> chordProgression, Pair<String, String> melodyKey,
                                     double crossoverProbability, List<Pair<String, Double>> crossoverType,
                                     double mutationProbability, List<Pair<String, Double>> mutationType,
                                     String selectionType, String matingPoolSelectionType,
                                     int numberOfGenerations, int numberOfIteration,
                                     List<String> criteria, HashMap<String,Pair<Double, Double>> criteriaRanges,
                                     Pair<Boolean, Integer> saveToJSON, String folderName, boolean play) {
        this.popSize = popSize;
        this.numberOfBars = numberOfBars;
        this.maxNumberOfNotes = maxNumberOfNotes;
        this.representationType = representationType;
        this.chordProgression = chordProgression;
        this.melodyKey = melodyKey;
        this.crossoverProbability = crossoverProbability;
        this.crossoverType = crossoverType;
        this.mutationProbability = mutationProbability;
        this.mutationType = mutationType;
        this.selectionType = selectionType;
        this.matingPoolSelectionType = matingPoolSelectionType;
        this.numberOfGenerations = numberOfGenerations;
        this.numberOfIteration = numberOfIteration;
        this.criteria = criteria;
        this.criteriaRanges = criteriaRanges;
        this.saveToJSON = saveToJSON;
        this.folderName = folderName;
        this.play = play;
    }

    public abstract void run();

    public List<Pair<String, Double>> getCrossoverType() {
        return crossoverType;
    }

    public List<Pair<String, Double>> getMutationType() {
        return mutationType;
    }

    public double getCrossoverProbability(){
        return crossoverProbability;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public String getSelectionType() {
        return selectionType;
    }

    public String getMatingPoolSelectionType() {
        return matingPoolSelectionType;
    }

    public String getRepresentationType() {
        return representationType;
    }

    public int getPopSize() {
        return popSize;
    }

    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }
}
