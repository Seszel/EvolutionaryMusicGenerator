package evolution.population;

import evolution.solution.melody.MelodyRepresentationInt;

import java.util.ArrayList;

public class PopulationRepresentationInt extends APopulation {
    private ArrayList<MelodyRepresentationInt> population;

    public PopulationRepresentationInt(int populationSize, ArrayList<MelodyRepresentationInt> population) {
        super(populationSize);
        this.population = population;
    }

    public ArrayList<MelodyRepresentationInt> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<MelodyRepresentationInt> population) {
        this.population = population;
    }
}
