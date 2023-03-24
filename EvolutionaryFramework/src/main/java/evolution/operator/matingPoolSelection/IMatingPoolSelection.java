package evolution.operator.matingPoolSelection;

import evolution.population.Population;
import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public interface IMatingPoolSelection {
    static <T> ArrayList<Pair<Individual, Individual>> matingPoolSelection(int numberOfParents, int popSize, T population) {
        System.out.println("MatingPoolSelection operator");
        return null;
    }
}
