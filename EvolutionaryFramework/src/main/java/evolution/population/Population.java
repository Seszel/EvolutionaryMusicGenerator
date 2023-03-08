package evolution.population;

import evolution.music.Melody;
import evolution.solution.Individual;

import java.util.ArrayList;

public class Population {
    private final int popSize;
    private final String representationType;
    private final int numberOfBars;
    private ArrayList<Individual> population;

    public Population(int popSize, String representationType, int numberOfBars){
        this.popSize = popSize;
        this.representationType = representationType;
        this.numberOfBars = numberOfBars;
    }


    public ArrayList<Individual> getPopulation() {
        return population;
    }

    public void setPopulation() {
        ArrayList<Individual> population = new ArrayList<>();
        Melody melody = new Melody(numberOfBars, representationType);
        for (int n = 0; n< popSize; n++){
            melody.initializeMelody();
            Individual individual = new Individual(n+1,melody);
            population.add(individual);
        }
        this.population = population;
    }
}
