package evolution.algorithm;

abstract class AEvolutionaryAlgorithm{
    private final int crossoverType;
    private final int mutationType;
    private final int selectionType;
    private final int matingPoolSelectionType;

    public AEvolutionaryAlgorithm(int crossoverType, int mutationType, int selectionType, int matingPoolSelectionType) {
        this.crossoverType = crossoverType;
        this.mutationType = mutationType;
        this.selectionType = selectionType;
        this.matingPoolSelectionType = matingPoolSelectionType;
    }

    public abstract void algorithm();

    public int getCrossoverType() {
        return crossoverType;
    }

    public int getMutationType() {
        return mutationType;
    }

    public int getSelectionType() {
        return selectionType;
    }

    public int getMatingPoolSelectionType() {
        return matingPoolSelectionType;
    }
}
