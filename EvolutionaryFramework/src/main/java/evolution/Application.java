package evolution;

import evolution.algorithm.Nsga_II;


public class Application {

    public static void main(String[] args) {

        int POP_SIZE = 10;
        int NUMBER_OF_BARS = 4;
        int MAX_NUMBER_OF_NOTES = 16;
        String REPRESENTATION_TYPE = "f1";
        String CROSSOVER_TYPE = "onePoint";
        String MUTATION_TYPE = "simple";
        String SELECTION_TYPE = "elitist";
        String MATING_POOL_SELECTION_TYPE = "distance";

        Nsga_II algorithm = new Nsga_II(
                POP_SIZE,
                NUMBER_OF_BARS,
                MAX_NUMBER_OF_NOTES,
                REPRESENTATION_TYPE,
                CROSSOVER_TYPE,
                MUTATION_TYPE,
                SELECTION_TYPE,
                MATING_POOL_SELECTION_TYPE);
        algorithm.run();

    }

}
