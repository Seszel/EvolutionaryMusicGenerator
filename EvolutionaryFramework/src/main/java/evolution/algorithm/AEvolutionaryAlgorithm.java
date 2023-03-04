package evolution.algorithm;

abstract class AEvolutionaryAlgorithm{
    private final String popSize;
    private final String representationType;
    private final String crossoverType;
    private final String mutationType;
    private final String selectionType;
    private final String matingPoolSelectionType;

    protected AEvolutionaryAlgorithm(String popSize, String representationType, String crossoverType, String mutationType, String selectionType, String matingPoolSelectionType) {
        this.popSize = popSize;
        this.representationType = representationType;
        this.crossoverType = crossoverType;
        this.mutationType = mutationType;
        this.selectionType = selectionType;
        this.matingPoolSelectionType = matingPoolSelectionType;
    }

    public abstract void algorithm();

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

    public String getPopSize() {
        return popSize;
    }
}
