package evolution.algorithm;

import com.google.common.collect.ImmutableList;
import evolution.music.Representation;
import evolution.population.PopulationMOEA_D;
import evolution.solution.Individual;
import evolution.util.Util;
import org.jfugue.player.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MOEA_D extends AEvolutionaryAlgorithm {
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

//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH:mm:ss");
//        String folderName = dtf.format(now);
//        Util.createDirectory(folderName);

        population.setExternalPopulation();
        population.setWeightVectors();
        population.setEuclideanDistancesWeightVectors();
        population.setNeighbours(numberOfNeighbours);
        population.generatePopulation(representation);
        population.setReferencePointsZ();

        for (int i = 0; i < numberOfIterations; i++) {
            System.out.println("Iteration number " + (i + 1));
            for (int g = 0; g < numberOfGenerations; g++) {
                population.update(representation);
            }

        }

        for (Individual individual : population.getPopulation()) {
            player.play(individual.getGenome().getMelodyJFugue());
//            break;
        }


        System.out.println("MOEA/D ended his work!");


    }
}
