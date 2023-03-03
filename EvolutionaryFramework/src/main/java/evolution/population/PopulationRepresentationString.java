package evolution.population;

import evolution.solution.melody.MelodyRepresentationInt;

import java.util.ArrayList;

public class PopulationRepresentationString extends APopulation{
    private ArrayList<MelodyRepresentationInt> population;

    public PopulationRepresentationString(int populationSize) {
        super(populationSize);
    }

    public ArrayList<MelodyRepresentationInt> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<MelodyRepresentationInt> population) {
        this.population = population;
    }
}
