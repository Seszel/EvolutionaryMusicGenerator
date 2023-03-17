package evolution;

import com.sun.tools.javac.util.List;
import evolution.algorithm.NSGA_II;


public class Application {

    public static void main(String[] args) {

        int POP_SIZE = 10;
        int NUMBER_OF_BARS = 4;
        int MAX_NUMBER_OF_NOTES = 16;
        String REPRESENTATION_TYPE = "f1";
        List<String> CHORD_PROGRESSION = List.of("I", "V", "vi", "IV");
        String MELODY_KEY = "C";
        String CROSSOVER_TYPE = "onePoint";
        String MUTATION_TYPE = "simple";
        String SELECTION_TYPE = "elitist";
        String MATING_POOL_SELECTION_TYPE = "distance";
        int NUMBER_OF_GENERATIONS = 10;
        List<String> CRITERIA = List.of("stability", "tension");

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
                CRITERIA);
        algorithm.run();

    }

}
