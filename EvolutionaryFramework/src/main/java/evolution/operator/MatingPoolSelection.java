package evolution.operator;

import evolution.population.Population;
import evolution.population.PopulationMOEA_D;
import evolution.solution.Individual;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatingPoolSelection {


    public static Pair<Individual, Individual> randomFromNeighbourhood(int numberOfNeighbours, PopulationMOEA_D population, int idxIndividual) {

        int idx1, idx2;
        idx1 = Util.getRandomNumber(0, numberOfNeighbours);
        idx2 = Util.getRandomNumber(0, numberOfNeighbours);
        while (idx2 == idx1){
            idx2 = Util.getRandomNumber(0, numberOfNeighbours - 1);
        }

        Pair<Integer, Integer> neighboursIdx = new ImmutablePair<>(
                population.getNeighbours().get(idxIndividual).get(idx1),
                population.getNeighbours().get(idxIndividual).get(idx2)
        );

        return new ImmutablePair<>(
                population.getPopulation().get(neighboursIdx.getLeft()),
                population.getPopulation().get(neighboursIdx.getRight())
        );

    }

    public static List<Pair<Individual, Individual>> tournament(int numberOfParents, int popSize, Population population) {
        List<Pair<Individual, Individual>> matingPool = new ArrayList<>();

        int idx1, idx2;
        for (int n = 0; n < numberOfParents; n++) {
            idx1 = Collections.max(List.of(Util.getRandomNumber(0, popSize), Util.getRandomNumber(0, popSize)));
            idx2 = Collections.max(List.of(Util.getRandomNumber(0, popSize), Util.getRandomNumber(0, popSize)));
            matingPool.add(new MutablePair<>(population.getPopulation().get(idx1), population.getPopulation().get(idx2)));
        }
        return matingPool;
    }
}
