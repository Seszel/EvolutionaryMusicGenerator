package evolution;

import evolution.algorithm.evolutionaryAlgorithm.Nsga_II;

public class Application {

    public static void main(String[] args) {
        Nsga_II algorithm = new Nsga_II(1,1,1,1);
        System.out.println(algorithm.getCrossoverType());
        algorithm.algorithm();
    }

}
