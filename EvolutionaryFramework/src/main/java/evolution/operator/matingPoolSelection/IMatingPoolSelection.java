package evolution.operator.matingPoolSelection;

import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public interface IMatingPoolSelection {
    static ArrayList<Pair<Individual, Individual>> matingPoolSelection(int numberOfParents, int popSize, ArrayList<Individual> population) {
        System.out.println("MatingPoolSelection operator");
        return null;
    }
}
