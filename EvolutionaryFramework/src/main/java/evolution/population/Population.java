package evolution.population;

import evolution.solution.Melody;

import java.util.ArrayList;

public class Population<T> {
    private final int populationSize;
    private ArrayList<Melody<T>> population;

    public Population(int populationSize, ArrayList<Melody<T>> population) {
        this.populationSize = populationSize;
        this.population = population;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public ArrayList<Melody<T>> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Melody<T>> population) {
        this.population = population;
    }
}
