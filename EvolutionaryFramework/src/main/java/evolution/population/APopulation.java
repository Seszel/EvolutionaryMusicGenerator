package evolution.population;

abstract class APopulation {
    private final int populationSize;

    public APopulation(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getPopulationSize() {
        return populationSize;
    }
}
