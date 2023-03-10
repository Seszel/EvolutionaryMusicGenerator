package evolution.algorithm;

import com.sun.tools.javac.util.List;
import evolution.population.Population;
import evolution.solution.Individual;
import org.jfugue.player.Player;

public class Nsga_II extends AEvolutionaryAlgorithm {

    public Nsga_II(int popSize, int numberOfBars, int maxNumberOfNotes, String representationType, List<String> chordProgression, String melodyKey, String crossoverType, String mutationType, String selectionType, String matingPoolSelectionType, List<String> criteria) {
        super(popSize, numberOfBars, maxNumberOfNotes, representationType, chordProgression, melodyKey, crossoverType, mutationType, selectionType, matingPoolSelectionType, criteria);
    }

    @Override
    public void run() {
        Population population = new Population(popSize, representationType, numberOfBars, maxNumberOfNotes, chordProgression, melodyKey);
        population.setPopulation();
        Player player = new Player();
        for (Individual individual : population.getPopulation()) {
            player.play(individual.getGenome().getMelodyJFugue());
        }
        System.out.println("Nsga_II algorithm!");

    }


}
