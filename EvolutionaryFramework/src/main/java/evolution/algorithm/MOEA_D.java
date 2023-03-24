package evolution.algorithm;

import com.google.common.collect.ImmutableList;
import evolution.music.Representation;
import evolution.population.PopulationMOEA_D;
import org.jfugue.player.Player;

import java.util.List;

public class MOEA_D extends AEvolutionaryAlgorithm{
    private final int numberOfNeighbours;
    public MOEA_D(int popSize, int numberOfBars, int maxNumberOfNotes, String representationType, List<String> chordProgression, String melodyKey, String crossoverType, String mutationType, String selectionType, String matingPoolSelectionType, int numberOfGenerations, int numberOfIterations, List<String> criteria, int numberOfNeighbours) {
        super(popSize, numberOfBars, maxNumberOfNotes, representationType, chordProgression, melodyKey, crossoverType, mutationType, selectionType, matingPoolSelectionType, numberOfGenerations, numberOfIterations, criteria);

        this.numberOfNeighbours = numberOfNeighbours;
    }

    @Override
    public void run() {

        PopulationMOEA_D population = new PopulationMOEA_D(popSize, representationType, criteria, numberOfBars, maxNumberOfNotes, chordProgression, melodyKey);
        ImmutableList<Integer> representation = Representation.getRepresentationInt(representationType);
        Player player = new Player();

        population.setWeightVectors();
        population.setEuclideanDistancesWeightVectors();
        population.setNeighbours(numberOfNeighbours);
        population.generatePopulation(representation);

        for (int g=0; g<numberOfGenerations; g++){
            population.reproduction(representation);
        }

        System.out.println("MOEA/D ended his work!");


    }
}
