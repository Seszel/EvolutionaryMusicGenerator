package evolution.operator;

import evolution.population.Population;
import evolution.solution.Individual;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatingPoolSelection {

    public static Pair<Integer, Integer> randomFromNeighbourhood(int numberOfNeighbours) {

        int idx1, idx2;
        idx1 = Util.getRandomNumber(0, numberOfNeighbours - 1);
        idx2 = Util.getRandomNumber(0, numberOfNeighbours - 1);

        return new ImmutablePair<>(idx1, idx2);


    }

    public static List<Pair<Individual, Individual>> tournament(int numberOfParents, int popSize, Population population) {
        List<Pair<Individual, Individual>> matingPool = new ArrayList<>();

        int idx1, idx2;
        for (int n = 0; n < numberOfParents; n++) {
            idx1 = Collections.max(List.of(Util.getRandomNumber(0, popSize - 1), Util.getRandomNumber(0, popSize - 1)));
            idx2 = Collections.max(List.of(Util.getRandomNumber(0, popSize - 1), Util.getRandomNumber(0, popSize - 1)));
            matingPool.add(new MutablePair<>(population.getPopulation().get(idx1), population.getPopulation().get(idx2)));
        }
        return matingPool;
    }
}
