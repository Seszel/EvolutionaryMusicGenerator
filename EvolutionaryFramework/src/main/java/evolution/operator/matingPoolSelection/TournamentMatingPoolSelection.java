package evolution.operator.matingPoolSelection;

import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Pair;
import evolution.util.Util;
import evolution.solution.Individual;

import java.util.ArrayList;
import java.util.Collections;

public class TournamentMatingPoolSelection implements IMatingPoolSelection {

    public static ArrayList<Pair<Individual, Individual>> matingPoolSelection(int numberOfParents, int popSize, ArrayList<Individual> population) {
        ArrayList<Pair<Individual, Individual>> matingPool = new ArrayList<>();

        int idx1, idx2;
        for (int n = 0; n < numberOfParents; n++) {
            idx1 = Collections.max(List.of(Util.getRandomNumber(0, popSize - 1), Util.getRandomNumber(0, popSize - 1)));
            idx2 = Collections.max(List.of(Util.getRandomNumber(0, popSize - 1), Util.getRandomNumber(0, popSize - 1)));
            matingPool.add(new Pair<>(population.get(idx1), population.get(idx2)));
        }
        return matingPool;
    }
}
