package evolution.algorithm;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

abstract class AEvolutionaryAlgorithm implements Runnable {
    protected final int popSize;
    protected final int numberOfBars;
    protected final int maxNumberOfNotes;
    protected final String representationType;
    protected final List<String> chordProgression;
    protected final Pair<String, String> melodyKey;
    protected final String crossoverType;
    protected final Pair<String, Double> mutationType;
    protected final String selectionType;
    protected final String matingPoolSelectionType;
    protected final int numberOfGenerations;
    protected final int numberOfIteration;
    protected final List<String> criteria;
    protected final Pair<Boolean, Integer> saveToJSON;
    protected final String folderName;

    protected AEvolutionaryAlgorithm(int popSize, int numberOfBars, int maxNumberOfNotes,
                                     String representationType, List<String> chordProgression,
                                     Pair<String, String> melodyKey, String crossoverType,
                                     Pair<String, Double> mutationType, String selectionType,
                                     String matingPoolSelectionType, int numberOfGenerations, int numberOfIteration,
                                     List<String> criteria, Pair<Boolean, Integer> saveToJSON, String folderName) {
        this.popSize = popSize;
        this.numberOfBars = numberOfBars;
        this.maxNumberOfNotes = maxNumberOfNotes;
        this.representationType = representationType;
        this.chordProgression = chordProgression;
        this.melodyKey = melodyKey;
        this.crossoverType = crossoverType;
        this.mutationType = mutationType;
        this.selectionType = selectionType;
        this.matingPoolSelectionType = matingPoolSelectionType;
        this.numberOfGenerations = numberOfGenerations;
        this.numberOfIteration = numberOfIteration;
        this.criteria = criteria;
        this.saveToJSON = saveToJSON;
        this.folderName = folderName;
    }

    public abstract void run();

    public String getCrossoverType() {
        return crossoverType;
    }

    public Pair<String, Double> getMutationType() {
        return mutationType;
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
