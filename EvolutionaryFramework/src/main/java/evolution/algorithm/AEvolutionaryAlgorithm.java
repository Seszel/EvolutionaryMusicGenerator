package evolution.algorithm;


import com.sun.tools.javac.util.List;

abstract class AEvolutionaryAlgorithm{
    protected final int popSize;
    protected final int numberOfBars;
    protected final int maxNumberOfNotes;
    protected final String representationType;
    protected final List<String> chordProgression;
    protected final String melodyKey;
    private final String crossoverType;
    private final String mutationType;
    private final String selectionType;
    private final String matingPoolSelectionType;
    protected final int numberOfGenerations;
    protected final List<String> criteria;

    protected AEvolutionaryAlgorithm(int popSize, int numberOfBars, int maxNumberOfNotes, String representationType, List<String> chordProgression, String melodyKey, String crossoverType, String mutationType, String selectionType, String matingPoolSelectionType, int numberOfGenerations, List<String> criteria) {
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
        this.criteria = criteria;
    }

    public abstract void run();

    public String getCrossoverType() {
        return crossoverType;
    }

    public String getMutationType() {
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
