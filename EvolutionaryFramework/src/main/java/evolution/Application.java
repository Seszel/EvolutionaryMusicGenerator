package evolution;

import evolution.algorithm.NSGA_II;

import java.util.List;


public class Application {

    private static final int POP_SIZE = 100;
    private static final int NUMBER_OF_BARS = 4;
    private static final int MAX_NUMBER_OF_NOTES = 16;
    private static final String REPRESENTATION_TYPE = "f1";
    private static final List<String> CHORD_PROGRESSION = List.of("I", "V", "vi", "IV");
    private static final String MELODY_KEY = "C";
    private static final String CROSSOVER_TYPE = "onePoint";
    private static final String MUTATION_TYPE = "simple";
    private static final String SELECTION_TYPE = "elitist";
    private static final String MATING_POOL_SELECTION_TYPE = "distance";
    private static final int NUMBER_OF_GENERATIONS = 5000;
    private static final int NUMBER_OF_ITERATIONS = 1;
    private static final List<String> CRITERIA = List.of("stability", "tension");

    public static void main(String[] args) {


        NSGA_II algorithm = new NSGA_II(
                POP_SIZE,
                NUMBER_OF_BARS,
                MAX_NUMBER_OF_NOTES,
                REPRESENTATION_TYPE,
                CHORD_PROGRESSION,
                MELODY_KEY,
                CROSSOVER_TYPE,
                MUTATION_TYPE,
                SELECTION_TYPE,
                MATING_POOL_SELECTION_TYPE,
                NUMBER_OF_GENERATIONS,
                NUMBER_OF_ITERATIONS,
                CRITERIA);

        algorithm.run();

    }

}
