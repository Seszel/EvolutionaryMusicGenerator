package evolution.stats;

import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

public class StatsMOEA_D extends Stats {

    private final int numberOfNeighbours;
    private HashMap<Integer, Individual> externalPopulation;

    public StatsMOEA_D(String algorithmName, int popSize,
                       int numberOfBars, int maxNumberOfNotes,
                       String representationType, List<String> chordProgression, Pair<String, String> melodyKey,
                       String crossoverType, Pair<String, Double> mutationType, String selectionType, String matingPoolSelectionType,
                       int numberOfGenerations, List<String> criteria,
                       int numberOfNeighbours, HashMap<Integer, Individual> externalPopulation) {
        super(algorithmName, popSize, numberOfBars, maxNumberOfNotes, representationType, chordProgression, melodyKey, crossoverType, mutationType, selectionType, matingPoolSelectionType, numberOfGenerations, criteria);
        this.numberOfNeighbours = numberOfNeighbours;
        this.externalPopulation = externalPopulation;
    }

    @Override
    public void updateStats() {

    }

    @Override
    public void writeToFile() {

    }
}
