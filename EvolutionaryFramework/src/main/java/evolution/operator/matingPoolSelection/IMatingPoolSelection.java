package evolution.operator.matingPoolSelection;

import com.sun.tools.javac.util.Pair;
import evolution.solution.Individual;

import java.util.ArrayList;

public interface IMatingPoolSelection {
    static ArrayList<Pair<Individual, Individual>> matingPoolSelection(int numberOfParents, int popSize, ArrayList<Individual> population) {
        System.out.println("MatingPoolSelection operator");
        return null;
    }
}
