package evolution.population;

import evolution.music.Melody;

import java.util.ArrayList;

public class Population<T> {
    private final int populationSize;
    private ArrayList<Melody<T>> population;

    public Population(int populationSize){
        this.populationSize = populationSize;
    }
    public Population(int populationSize, ArrayList<Melody<T>> population) {
        this.populationSize = populationSize;
        this.population = population;
    }

    public void initializePopulation(){}

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
