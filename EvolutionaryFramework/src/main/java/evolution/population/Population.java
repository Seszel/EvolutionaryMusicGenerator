package evolution.population;

import com.google.common.collect.ImmutableList;
import evolution.music.Melody;
import evolution.music.Representation;
import evolution.solution.Individual;

import java.util.ArrayList;

public class Population {
    private final int popSize;
    private final String representationType;
    private final int numberOfBars;
    private final int maxNumberOfNotes;
    private ArrayList<Individual> population;

    public Population(int popSize, String representationType, int numberOfBars, int maxNumberOfNotes){
        this.popSize = popSize;
        this.representationType = representationType;
        this.numberOfBars = numberOfBars;
        this.maxNumberOfNotes = maxNumberOfNotes;
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }

    public void setPopulation() {
        ArrayList<Individual> population = new ArrayList<>();
        ImmutableList<Integer> representation = Representation.getRepresentationInt(representationType);
        Melody melody = new Melody(numberOfBars, maxNumberOfNotes, representationType);
        for (int n = 0; n< popSize; n++){
            assert representation != null;
            melody.initializeMelody(representation);
            Individual individual = new Individual(n+1,melody);
            population.add(individual);
        }
        this.population = population;
    }
}
