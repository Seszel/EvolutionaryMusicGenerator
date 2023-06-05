package evolution;

import evolution.algorithm.MOEA_D;
import evolution.algorithm.NSGA_II;
import evolution.algorithm.NSGA_II_oneCriterion;
import evolution.stats.Stats;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;


public class Application {

//    private static final String ALGORITHM = "NSGA_II";
    private static final String ALGORITHM = "NSGA_II_oneCriterion";
//    private static final String ALGORITHM = "MOEA_D";
    private static final int POP_SIZE = 100;
    private static final int NUMBER_OF_BARS = 4;
    private static final int MAX_NUMBER_OF_NOTES = 16;
    private static final String REPRESENTATION_TYPE = "f1";
    private static final List<String> CHORD_PROGRESSION = List.of("I", "V", "vi", "IV");
    private static final Pair<String, String> MELODY_KEY = new ImmutablePair<>("C", "MAJOR");
    private static final double CROSSOVER_PROBABILITY = 0.9;
    private static final List<Pair<String, Double>> CROSSOVER_TYPE = List.of(
            new ImmutablePair<>("ONE_POINT_CROSSOVER", 0.9),
            new ImmutablePair<>("TWO_POINT_CROSSOVER", 0.1)
    );
    private static final double MUTATION_PROBABILITY = 0.8;
    private static final List<Pair<String, Double>> MUTATION_TYPE = List.of(
            new ImmutablePair<>("SIMPLE", 5.0),
            new ImmutablePair<>("BAR_ORDER", 0.0),
            new ImmutablePair<>("ADD_ZERO", 0.0),
            new ImmutablePair<>("ADD_REST", 2.0),
            new ImmutablePair<>("MUSICAL_CONTEXT", 3.0)
    );
    private static final String SELECTION_TYPE = "";
    private static final String MATING_POOL_SELECTION_TYPE = "";
    private static final int NUMBER_OF_GENERATIONS = 100;
    private static final int NUMBER_OF_ITERATIONS = 1;
//    private static final List<String> CRITERIA = List.of("NON_PERFECT_INTERVAL", "PERFECT_INTERVAL");
//    private static final List<String> CRITERIA = List.of("SKIP_MOTION", "STEP_MOTION");
//    private static final List<String> CRITERIA = List.of("NON_CHORD_TONE", "CHORD_TONE");
//    private static final List<String> CRITERIA = List.of("STABILITY", "TENSION");
//    private static final List<String> CRITERIA = List.of("DESCENDING_MELODY_LINE", "ASCENDING_MELODY_LINE");
//    private static final List<String> CRITERIA = List.of("SIMPLE_RHYTHM", "COMPLICATED_RHYTHM");
    private static final List<String> CRITERIA = List.of("CHORD_TONE");
    private static final HashMap<String,Pair<Double, Double>> CRITERIA_RANGES = new HashMap<>()
    {{
        put("STABILITY", new ImmutablePair<>(-123.0,240.0));
        put("TENSION", new ImmutablePair<>(-45.0,117.0));
        put("CHORD_TONE", new ImmutablePair<>(0.0,1.0));
        put("NON_CHORD_TONE", new ImmutablePair<>(0.0,1.0));
        put("STEP_MOTION", new ImmutablePair<>(0.0,1.0));
        put("SKIP_MOTION", new ImmutablePair<>(0.0,1.0));
        put("PERFECT_INTERVAL", new ImmutablePair<>(0.0,1.0));
        put("NON_PERFECT_INTERVAL", new ImmutablePair<>(0.0,1.0));
        put("ASCENDING_MELODY_LINE", new ImmutablePair<>(0.0,1.0));
        put("DESCENDING_MELODY_LINE", new ImmutablePair<>(0.0,1.0));
        put("SIMPLE_RHYTHM", new ImmutablePair<>(0.0,1.0));
        put("COMPLICATED_RHYTHM", new ImmutablePair<>(0.0,1.0));
    }};
    private static final Pair<Boolean, Integer> SAVE_TO_JSON = new ImmutablePair<>(false, 1);
    private static final int NUMBER_OF_NEIGHBOURS = 10;
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
                            CROSSOVER_PROBABILITY,
                            CROSSOVER_TYPE,
                            MUTATION_PROBABILITY,
                            MUTATION_TYPE,
                            SELECTION_TYPE,
                            MATING_POOL_SELECTION_TYPE,
                            NUMBER_OF_GENERATIONS,
                            i,
                            CRITERIA,
                            CRITERIA_RANGES,
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
                            CROSSOVER_PROBABILITY,
                            CROSSOVER_TYPE,
                            MUTATION_PROBABILITY,
                            MUTATION_TYPE,
                            SELECTION_TYPE,
                            MATING_POOL_SELECTION_TYPE,
                            NUMBER_OF_GENERATIONS,
                            i,
                            CRITERIA,
                            CRITERIA_RANGES,
                            SAVE_TO_JSON,
                            folderName,
                            PLAY,
                            NUMBER_OF_NEIGHBOURS
                    );
                    Thread t = new Thread(algorithm_MOEA_D);
                    t.start();
                }
                break;
            case "NSGA_II_oneCriterion":
                if (SAVE_TO_JSON.getLeft()){
                    folderName = "NSGA_II_oneCriterion/" + folderName;
                    Stats.createDirectory(folderName);
                }
                for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                    NSGA_II_oneCriterion algorithm_NSGA_II_oneCriterion = new NSGA_II_oneCriterion(
                            POP_SIZE,
                            NUMBER_OF_BARS,
                            MAX_NUMBER_OF_NOTES,
                            REPRESENTATION_TYPE,
                            CHORD_PROGRESSION,
                            MELODY_KEY,
                            CROSSOVER_PROBABILITY,
                            CROSSOVER_TYPE,
                            MUTATION_PROBABILITY,
                            MUTATION_TYPE,
                            SELECTION_TYPE,
                            MATING_POOL_SELECTION_TYPE,
                            NUMBER_OF_GENERATIONS,
                            i,
                            CRITERIA,
                            CRITERIA_RANGES,
                            SAVE_TO_JSON,
                            folderName,
                            PLAY
                    );
                    Thread t = new Thread(algorithm_NSGA_II_oneCriterion);
                    t.start();
                }
                break;

        }

    }

}
