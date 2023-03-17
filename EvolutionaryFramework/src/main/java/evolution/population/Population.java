package evolution.population;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.sun.tools.javac.util.List;
import evolution.music.Melody;
import evolution.music.Representation;
import evolution.solution.Individual;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Population {
    protected final int popSize;
    private final String representationType;
    private final int numberOfBars;
    private final int maxNumberOfNotes;
    protected final List<String> chordProgression;
    protected final String melodyKey;
    protected ArrayList<Individual> population;

    public Population(int popSize, String representationType, int numberOfBars, int maxNumberOfNotes, List<String> chordProgression, String melodyKey){
        this.popSize = popSize;
        this.representationType = representationType;
        this.numberOfBars = numberOfBars;
        this.maxNumberOfNotes = maxNumberOfNotes;
        this.chordProgression = chordProgression;
        this.melodyKey = melodyKey;
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }

    public void assignPopulation(ArrayList<Individual> population){
        this.population = population;
    }
    public void setPopulation(ImmutableList<Integer> representation) {
        ArrayList<Individual> population = new ArrayList<>();
        ArrayList<HashMap<String, List<Integer>>> chordProgressionPattern = Representation.getChordProgressionMajor();
        BiMap<String, Integer> notesMap = Representation.getNotesMap();
        int melodyKeyValue = notesMap.get(melodyKey);

        for (int n = 0; n< popSize; n++){
            Melody melody = new Melody(numberOfBars, maxNumberOfNotes, representationType);
            assert representation != null;
            melody.initializeMelody(representation);
            melody.setMelodyJFugue(); //not necessary
            Individual individual = new Individual(melody);
            individual.setFitness1(chordProgressionPattern, chordProgression, melodyKeyValue);
            individual.setFitness2(chordProgressionPattern, chordProgression, melodyKeyValue);
            population.add(individual);
        }
        this.population = population;
    }

    public abstract void generateFronts();

}
