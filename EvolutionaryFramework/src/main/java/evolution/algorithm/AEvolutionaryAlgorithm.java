package evolution.algorithm;

import java.util.Map;

abstract class AEvolutionaryAlgorithm{
    protected final int popSize;
    protected final int numberOfBars;
    protected final String representationType;
    private final String crossoverType;
    private final String mutationType;
    private final String selectionType;
    private final String matingPoolSelectionType;
    protected Map<String, Integer> representationMap;

    protected AEvolutionaryAlgorithm(int popSize, int numberOfBars, String representationType, String crossoverType, String mutationType, String selectionType, String matingPoolSelectionType) {
        this.popSize = popSize;
        this.numberOfBars = numberOfBars;
        this.representationType = representationType;
        this.crossoverType = crossoverType;
        this.mutationType = mutationType;
        this.selectionType = selectionType;
        this.matingPoolSelectionType = matingPoolSelectionType;
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

    public Map<String, Integer> getRepresentationMap() {
        return representationMap;
    }

}
