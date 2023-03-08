package evolution;

import evolution.algorithm.Nsga_II;
import evolution.music.RepresentationMap;

import java.util.Map;

public class Application {

    public static void main(String[] args) {

        Nsga_II algorithm = new Nsga_II(10, 4,"f1", "onePoint", "simple", "elitist", "distance");
        algorithm.run();

    }

}
