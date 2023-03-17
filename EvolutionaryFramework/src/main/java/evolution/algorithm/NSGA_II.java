package evolution.algorithm;

import com.google.common.collect.ImmutableList;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Pair;
import evolution.helper.Helper;
import evolution.music.Representation;
import evolution.operator.matingPoolSelection.TournamentMatingPoolSelection;
import evolution.population.PopulationNSGA_II;
import evolution.solution.Individual;
import org.jfugue.player.Player;

import java.util.ArrayList;

public class NSGA_II extends AEvolutionaryAlgorithm {

    public NSGA_II(int popSize, int numberOfBars, int maxNumberOfNotes, String representationType, List<String> chordProgression, String melodyKey, String crossoverType, String mutationType, String selectionType, String matingPoolSelectionType, int numberOfGenerations, List<String> criteria) {
        super(popSize, numberOfBars, maxNumberOfNotes, representationType, chordProgression, melodyKey, crossoverType, mutationType, selectionType, matingPoolSelectionType, numberOfGenerations, criteria);
    }

    @Override
    public void run() {
        PopulationNSGA_II population = new PopulationNSGA_II(popSize, representationType, criteria, numberOfBars, maxNumberOfNotes, chordProgression, melodyKey);
        ImmutableList<Integer> representation = Representation.getRepresentationInt(representationType);
        Player player = new Player();

        population.generatePopulation(representation);
        population.generateFronts();
        population.crowdingDistanceAssignment();
        population.crowdedComparisonOperator();

//        for (Individual individual : population.getPopulation()) {
//            player.play(individual.getGenome().getMelodyJFugue());
//        }

        for (int n=0; n<numberOfGenerations; n++){
            ArrayList<Pair<Individual, Individual>> matingPool = TournamentMatingPoolSelection.matingPoolSelection(10,popSize,population.getPopulation());
            population.createOffsprings(matingPool, representation);
            population.changePopulation();
            population.generateFronts();
            // poprawic, aby nie sortowac wszystkich tylko az do osiagniecia popsizu
            population.crowdingDistanceAssignment();
            population.crowdedComparisonOperator();
            population.setPopulation(new ArrayList<>(Helper.flattenListOfListsStream(population.getFronts()).subList(0, popSize)));
            Helper.generateJSONFile(population.getPopulation(), n, 0);
        }
//        for (Individual individual : population.getPopulation()) {
//            player.play(individual.getGenome().getMelodyJFugue());
//        }
        System.out.println("Nsga_II algorithm!");
    }


}
