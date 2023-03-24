package evolution.operator.matingPoolSelection;

import evolution.population.Population;
import evolution.util.Util;
import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TournamentMatingPoolSelection implements IMatingPoolSelection {

    public static ArrayList<Pair<Individual, Individual>> matingPoolSelection(int numberOfParents, int popSize, Population population) {
        ArrayList<Pair<Individual, Individual>> matingPool = new ArrayList<>();

        int idx1, idx2;
        for (int n = 0; n < numberOfParents; n++) {
            idx1 = Collections.max(List.of(Util.getRandomNumber(0, popSize - 1), Util.getRandomNumber(0, popSize - 1)));
            idx2 = Collections.max(List.of(Util.getRandomNumber(0, popSize - 1), Util.getRandomNumber(0, popSize - 1)));
            matingPool.add(new MutablePair<>(population.getPopulation().get(idx1), population.getPopulation().get(idx2)));
        }
        return matingPool;
    }
}
