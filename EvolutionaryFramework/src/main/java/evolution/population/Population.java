package evolution.population;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.sun.tools.javac.util.List;
import evolution.music.Melody;
import evolution.music.Representation;
import evolution.solution.Individual;

import java.util.ArrayList;
import java.util.HashMap;

public class Population {
    private final int popSize;
    private final String representationType;
    private final int numberOfBars;
    private final int maxNumberOfNotes;
    private final List<String> chordProgression;
    private final String melodyKey;
    private ArrayList<Individual> population;

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

    public void setPopulation() {
        ArrayList<Individual> population = new ArrayList<>();
        ImmutableList<Integer> representation = Representation.getRepresentationInt(representationType);
        ArrayList<HashMap<String, List<Integer>>> chordProgressionPattern = Representation.getChordProgressionMajor();
        BiMap<String, Integer> notesMap = Representation.getNotesMap();
        int melodyKeyValue = notesMap.get(melodyKey);

        for (int n = 0; n< popSize; n++){
            Melody melody = new Melody(numberOfBars, maxNumberOfNotes, representationType);
            assert representation != null;
            melody.initializeMelody(representation);
            melody.setMelodyJFugue(); //not necessary
            Individual individual = new Individual(n+1,melody, chordProgressionPattern, chordProgression, melodyKeyValue);
            individual.setFitness1();
            individual.setFitness2();
            population.add(individual);
        }
        this.population = population;
    }
}
