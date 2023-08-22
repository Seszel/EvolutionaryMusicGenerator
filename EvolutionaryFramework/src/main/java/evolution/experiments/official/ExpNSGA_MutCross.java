package evolution.experiments.official;

import evolution.algorithm.NSGA_II;
import evolution.stats.Stats;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class ExpNSGA_MutCross {

    private static final String ALGORITHM = "NSGA_II";
    private static final int NUMBER_OF_BARS = 4;
    private static final int MAX_NUMBER_OF_NOTES = 16;
    private static final String REPRESENTATION_TYPE = "f1";
    private static final List<List<String>> CHORD_PROGRESSION = List.of(
            List.of("I", "V", "vi", "IV")
    );
    private static final List<Pair<String, String>> MELODY_KEY = List.of(
            new ImmutablePair<>("C", "MAJOR")
    );
    private static final int POP_SIZE = 500;
    private static final HashMap<String, Double> WEIGHTS = new HashMap<>(){{
        put("CHORD_TONE", 10.0);
        put("NON_CHORD_TONE", 10.0);
        put("STEP_MOTION", 4.0);
        put("SKIP_MOTION", 4.0);
        put("DESCENDING_MELODY_LINE", 2.0);
        put("ASCENDING_MELODY_LINE", 2.0);
//        put("PERFECT_INTERVAL", 2.0);
//        put("NON_PERFECT_INTERVAL", 2.0);
        put("SIMPLE_RHYTHM", 5.0);
        put("COMPLICATED_RHYTHM", 5.0);
        put("UNDESIRABLE_PROPERTIES_MELODY", 21.0);
    }
    };
    private static final List<Double> CROSSOVER_PROBABILITY = List.of(0.5, 0.6, 0.7, 0.8, 0.9, 0.99);
    private static final List<Pair<String, Double>> CROSSOVER_TYPE = List.of(
            new ImmutablePair<>("ONE_POINT_CROSSOVER", 2.0),
            new ImmutablePair<>("TWO_POINT_CROSSOVER", 1.0),
            new ImmutablePair<>("MUSICAL_CONTEXT", 4.0)
    );
    private static final List<Double> MUTATION_PROBABILITY = List.of(0.1, 0.2, 0.3, 0.4, 0.5, 0.6);
    private static final List<Pair<String, Double>> MUTATION_TYPE = List.of(
            new ImmutablePair<>("SIMPLE", 2.0),
//            new ImmutablePair<>("BAR_ORDER", 0.0),
            new ImmutablePair<>("ADD_ZERO", 1.0),
            new ImmutablePair<>("ADD_REST", 0.5),
            new ImmutablePair<>("SWAP_NOTES", 3.0),
            new ImmutablePair<>("SWAP_DURATION", 6.0),
            new ImmutablePair<>("TRANSPOSE_NOTES", 3.0),
            new ImmutablePair<>("MUSICAL_CONTEXT", 10.0)
    );
    private static final String SELECTION_TYPE = "";
    private static final String MATING_POOL_SELECTION_TYPE = "";
    private static final int NUMBER_OF_GENERATIONS = 250;
    private static final int NUMBER_OF_ITERATIONS = 20;

    private static final List<String> CRITERIA = List.of("SIMPLE_AND_OBVIOUS", "COMPLICATED_AND_ENIGMATIC");

    private static final HashMap<String,Pair<Double, Double>> CRITERIA_RANGES = new HashMap<>()
    {{
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
        put("UNDESIRABLE_PROPERTIES_MELODY", new ImmutablePair<>(-12.0,0.0));
        put("SIMPLE_AND_OBVIOUS", new ImmutablePair<>(0.0,42.0));
        put("COMPLICATED_AND_ENIGMATIC", new ImmutablePair<>(0.0,42.0));
    }};

    private static final Pair<Boolean, Integer> SAVE_TO_JSON = new ImmutablePair<>(true, 1);
    private static final boolean PLAY = false;

    public static void main(String[] args) {


        for (int i = 0; i < CHORD_PROGRESSION.size(); i++) {
            System.out.println("\nProgression: " + CHORD_PROGRESSION.get(i) + ", key: " + MELODY_KEY.get(i));
            for (double crossoverProbability : CROSSOVER_PROBABILITY) {
                for (double mutationProbability : MUTATION_PROBABILITY) {
                    System.out.println("\nCrossover: " + crossoverProbability + ", Mutation: " + mutationProbability);
                    runAlgorithm(
                            new ImmutablePair<>(CHORD_PROGRESSION.get(i), MELODY_KEY.get(i)),
                            new ImmutablePair<>(crossoverProbability, mutationProbability)
                    );
                }
            }
        }
    }

    public static void runAlgorithm(Pair<List<String>, Pair<String, String>> parameters, Pair<Double, Double> probabilites) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH:mm:ss");
        String folderName = "ExpNSGA_MutCross/" + dtf.format(now);


        switch (ALGORITHM) {
            case "NSGA_II":
                if (SAVE_TO_JSON.getLeft()){
                    folderName = "NSGA_II/" + folderName;
                    Stats.createDirectory(folderName);
                }
                NSGA_II[] algorithms = new NSGA_II[NUMBER_OF_ITERATIONS];
                Thread[] threads = new Thread[NUMBER_OF_ITERATIONS];

                for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                    algorithms[i] = new NSGA_II(
                            POP_SIZE,
                            parameters.getKey().size(),
                            MAX_NUMBER_OF_NOTES,
                            REPRESENTATION_TYPE,
                            parameters.getKey(),
                            parameters.getValue(),
                            WEIGHTS,
                            probabilites.getKey(),
                            CROSSOVER_TYPE,
                            probabilites.getValue(),
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
                    threads[i] = new Thread(algorithms[i]);
                    threads[i].start();

                }

                // Wait for all threads to complete
                try {
                    for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                        threads[i].join();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
        }
    }


}
