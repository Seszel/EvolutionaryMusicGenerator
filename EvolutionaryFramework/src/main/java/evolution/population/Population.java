package evolution.population;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import evolution.music.Melody;
import evolution.music.Representation;
import evolution.solution.Individual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Population {
    protected final int popSize;
    protected final String representationType;
    protected final List<String> criteria;
    protected final int numberOfBars;
    protected final int maxNumberOfNotes;
    protected final List<String> chordProgression;
    protected final String melodyKey;
    protected ArrayList<Individual> population;

    public Population(int popSize, String representationType, List<String> criteria, int numberOfBars, int maxNumberOfNotes, List<String> chordProgression, String melodyKey) {
        this.popSize = popSize;
        this.representationType = representationType;
        this.criteria = criteria;
        this.numberOfBars = numberOfBars;
        this.maxNumberOfNotes = maxNumberOfNotes;
        this.chordProgression = chordProgression;
        this.melodyKey = melodyKey;
    }

    public void generatePopulation(ImmutableList<Integer> representation) {
        ArrayList<Individual> population = new ArrayList<>();
        ArrayList<HashMap<String, List<Integer>>> chordProgressionPattern = Representation.getChordProgressionMajor();
        BiMap<String, Integer> notesMap = Representation.getNotesMap();
        int melodyKeyValue = notesMap.get(melodyKey);

        for (int n = 0; n < popSize; n++) {
            Melody melody = new Melody();
            assert representation != null;
            melody.initializeMelody(representation, representationType, numberOfBars, maxNumberOfNotes);
            melody.setMelodyJFugue(maxNumberOfNotes);
            Individual individual = new Individual(melody);
            individual.setFitness(criteria, chordProgressionPattern, chordProgression, melodyKeyValue);
//            individual.setBestFitnessSoFar(individual.getFitness()); // for MOEA/D only
            population.add(individual);
        }
        this.population = population;
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Individual> population) {
        for (Individual individual : population) {
            individual.getGenome().setMelodyJFugue(maxNumberOfNotes);
        }
        this.population = population;
    }

}
