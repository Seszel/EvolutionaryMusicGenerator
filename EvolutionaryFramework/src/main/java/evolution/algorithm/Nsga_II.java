package evolution.algorithm;

import evolution.population.Population;
import evolution.solution.Individual;

public class Nsga_II extends AEvolutionaryAlgorithm {

    public Nsga_II(int popSize, int numberOfBars, int maxNumberOfNotes, String representationType, String crossoverType, String mutationType, String selectionType, String matingPoolSelectionType) {
        super(popSize, numberOfBars, maxNumberOfNotes, representationType, crossoverType, mutationType, selectionType, matingPoolSelectionType);
    }

    @Override
    public void run() {
        Population population = new Population(popSize, representationType, numberOfBars, maxNumberOfNotes);
        population.setPopulation();
//        for (Individual individual : population.getPopulation()) {
//
//        }

        System.out.println("Nsga_II algorithm!");

    }


}
