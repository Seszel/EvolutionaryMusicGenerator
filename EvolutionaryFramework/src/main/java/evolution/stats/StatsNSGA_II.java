package evolution.stats;

import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

public class StatsNSGA_II extends Stats {

    private HashMap<Integer, List<List<Individual>>> fronts;

    public StatsNSGA_II(String algorithmName, int popSize, int numberOfBars, int maxNumberOfNotes, String representationType, List<String> chordProgression, Pair<String, String> melodyKey, String crossoverType, Pair<String, Double> mutationType, String selectionType, String matingPoolSelectionType, int numberOfGenerations, List<String> criteria) {
        super(algorithmName, popSize, numberOfBars, maxNumberOfNotes, representationType, chordProgression, melodyKey, crossoverType, mutationType, selectionType, matingPoolSelectionType, numberOfGenerations, criteria);
    }

    @Override
    public void updateStats() {

    }

    @Override
    public void writeToFile() {

    }
}
