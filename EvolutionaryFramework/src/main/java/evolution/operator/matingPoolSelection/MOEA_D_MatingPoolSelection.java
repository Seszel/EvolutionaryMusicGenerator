package evolution.operator.matingPoolSelection;

import evolution.population.PopulationMOEA_D;
import evolution.solution.Individual;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class MOEA_D_MatingPoolSelection implements IMatingPoolSelection {
    public static ArrayList<Pair<Individual, Individual>> matingPoolSelection(int numberOfParents, int popSize, PopulationMOEA_D population) {
        ArrayList<Pair<Individual, Individual>> matingPool = new ArrayList<>();
        int neighbourSize = population.getNeighbours().get(0).size();

        int idx1, idx2;
        for (int n = 0; n < numberOfParents; n++) {
            idx1 = Util.getRandomNumber(0, neighbourSize - 1);
            idx2 = Util.getRandomNumber(0, neighbourSize - 1);
            matingPool.add(new ImmutablePair<>(population.getPopulation().get(population.getNeighbours().get(n).get(idx1)),
                    population.getPopulation().get(population.getNeighbours().get(n).get(idx2))));
        }

        return matingPool;


    }
}
