package evolution.stats;

import evolution.objective.EvaluationParameters;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public abstract class Stats {
    protected final String algorithmName;
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
    protected final List<String> criteria;


    public Stats(String algorithmName, int popSize, int numberOfBars, int maxNumberOfNotes,
                 String representationType, List<String> chordProgression, Pair<String, String> melodyKey,
                 String crossoverType, Pair<String,Double> mutationType, String selectionType,
                 String matingPoolSelectionType, int numberOfGenerations,List<String> criteria) {
        this.algorithmName = algorithmName;
        this.popSize = popSize;
        this.representationType = representationType;
        this.crossoverType = crossoverType;
        this.mutationType = mutationType;
        this.selectionType = selectionType;
        this.matingPoolSelectionType = matingPoolSelectionType;
        this.numberOfGenerations = numberOfGenerations;
        this.criteria = criteria;
        this.numberOfBars = numberOfBars;
        this.maxNumberOfNotes = maxNumberOfNotes;
        this.chordProgression = chordProgression;
        this.melodyKey = melodyKey;
    }

    public abstract void updateStats();
    public abstract void writeToFile();

}
