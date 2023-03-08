package evolution.algorithm;

import evolution.music.RepresentationMap;
import evolution.population.Population;

public class Nsga_II extends AEvolutionaryAlgorithm {

    public Nsga_II(int popSize, int numberOfBars, String representationType, String crossoverType, String mutationType, String selectionType, String matingPoolSelectionType) {
        super(popSize, numberOfBars, representationType, crossoverType, mutationType, selectionType, matingPoolSelectionType);
    }

    /**
     * Function which generate representation map, key value C4 - 60
     */
    public void setRepresentationMap() {

        RepresentationMap representation = new RepresentationMap();
        this.representationMap = representation.getRepresentation(representationType);

    }

    @Override
    public void run() {
        Population population = new Population(popSize, representationType, numberOfBars);
        population.setPopulation();
        System.out.println("Nsga_II algorithm!");

    }


}
