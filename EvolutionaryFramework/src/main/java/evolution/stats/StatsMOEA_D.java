package evolution.stats;

import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

public class StatsMOEA_D extends Stats {

    private final int numberOfNeighbours;
    private HashMap<Integer, List<Individual>> externalPopulationForGeneration = new HashMap<>();

    public StatsMOEA_D(String algorithmName, int popSize,
                       int numberOfBars, int maxNumberOfNotes,
                       String representationType, List<String> chordProgression, Pair<String, String> melodyKey,
                       String crossoverType, Pair<String, Double> mutationType, String selectionType, String matingPoolSelectionType,
                       int numberOfGenerations, List<String> criteria, String folderName,
                       int numberOfNeighbours) {
        super(algorithmName, popSize, numberOfBars, maxNumberOfNotes, representationType, chordProgression, melodyKey, crossoverType, mutationType, selectionType, matingPoolSelectionType, numberOfGenerations, criteria, folderName);
        this.numberOfNeighbours = numberOfNeighbours;
    }

    public void updateStats(int generationNumber, List<Individual> externalPopulation) {
        externalPopulationForGeneration.put(generationNumber, externalPopulation);
    }

    @Override
    public void generateJSON(int numberOfIteration) {

    }
}
