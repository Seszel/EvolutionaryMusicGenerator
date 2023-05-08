package evolution;

import evolution.algorithm.MOEA_D;
import evolution.algorithm.NSGA_II;
import evolution.stats.Stats;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Application {

//    private static final String ALGORITHM = "NSGA_II";
    private static final String ALGORITHM = "MOEA_D";
    private static final int POP_SIZE = 1000;
    private static final int NUMBER_OF_BARS = 4;
    private static final int MAX_NUMBER_OF_NOTES = 16;
    private static final String REPRESENTATION_TYPE = "f1";
    private static final List<String> CHORD_PROGRESSION = List.of("I", "V", "vi", "IV");
    private static final Pair<String, String> MELODY_KEY = new ImmutablePair<>("C", "MAJOR");
    private static final String CROSSOVER_TYPE = "ONE_POINT_CROSSOVER";
    private static final Pair<String, Double> MUTATION_TYPE = new ImmutablePair<>("SIMPLE", 0.5);
    private static final String SELECTION_TYPE = "";
    private static final String MATING_POOL_SELECTION_TYPE = "";
    private static final int NUMBER_OF_GENERATIONS = 100;
    private static final int NUMBER_OF_ITERATIONS = 1;
    private static final List<String> CRITERIA = List.of("STABILITY", "TENSION");
    private static final Pair<Boolean, Integer> SAVE_TO_JSON = new ImmutablePair<>(false, NUMBER_OF_GENERATIONS/10);
    private static final int NUMBER_OF_NEIGHBOURS = 50;
    private static final boolean PLAY = true;

    public static void main(String[] args) {
        runAlgorithm();
    }

    public static void runAlgorithm() {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH:mm:ss");
        String folderName = dtf.format(now);


        switch (ALGORITHM) {
            case "NSGA_II":
                if (SAVE_TO_JSON.getLeft()){
                    folderName = "NSGA_II/" + folderName;
                    Stats.createDirectory(folderName);
                }
                for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                    NSGA_II algorithm_NSGA_II = new NSGA_II(
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
                            i,
                            CRITERIA,
                            SAVE_TO_JSON,
                            folderName,
                            PLAY
                    );
                    Thread t = new Thread(algorithm_NSGA_II);
                    t.start();
                }
                break;
            case "MOEA_D":
                if (SAVE_TO_JSON.getLeft()){
                    folderName = "MOEA_D/" + folderName;
                    Stats.createDirectory(folderName);
                }
                for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                    MOEA_D algorithm_MOEA_D = new MOEA_D(
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
                            i,
                            CRITERIA,
                            SAVE_TO_JSON,
                            folderName,
                            PLAY,
                            NUMBER_OF_NEIGHBOURS
                    );
                    Thread t = new Thread(algorithm_MOEA_D);
                    t.start();
                }
                break;
        }

    }

}
