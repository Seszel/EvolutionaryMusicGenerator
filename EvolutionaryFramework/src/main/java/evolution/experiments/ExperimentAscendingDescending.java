package evolution.experiments;

import evolution.algorithm.NSGA_II;
import evolution.stats.Stats;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class ExperimentAscendingDescending {

    private static final String ALGORITHM = "NSGA_II";
    private static final int NUMBER_OF_BARS = 1;
    private static final int MAX_NUMBER_OF_NOTES = 16;
    private static final String REPRESENTATION_TYPE = "f1";
    private static final List<List<String>> CHORD_PROGRESSION = List.of(
            List.of("I")

    );
    private static final List<Pair<String, String>> MELODY_KEY = List.of(
            new ImmutablePair<>("C", "MAJOR")

    );
    private static final int POP_SIZE = 100;
    private static final HashMap<String, Double> WEIGHTS = new HashMap<>(){{
        put("DESCENDING_MELODY_LINE", 1.0);
        put("ASCENDING_MELODY_LINE", 1.0);
    }
    };
    private static final Double CROSSOVER_PROBABILITY = 0.9;
    private static final List<Pair<String, Double>> CROSSOVER_TYPE = List.of(
            new ImmutablePair<>("ONE_POINT_CROSSOVER", 4.0),
            new ImmutablePair<>("TWO_POINT_CROSSOVER", 1.0),
            new ImmutablePair<>("MUSICAL_CONTEXT", 0.0)
    );
    private static final Double MUTATION_PROBABILITY = 0.2;
    private static final List<Pair<String, Double>> MUTATION_TYPE = List.of(
            new ImmutablePair<>("SIMPLE", 10.0),
            new ImmutablePair<>("BAR_ORDER", 0.0),
            new ImmutablePair<>("ADD_ZERO", 1.0),
            new ImmutablePair<>("ADD_REST", 1.0),
            new ImmutablePair<>("SWAP_NOTES", 0.0),
            new ImmutablePair<>("SWAP_DURATION", 0.0),
            new ImmutablePair<>("TRANSPOSE_NOTES", 0.0),
            new ImmutablePair<>("MUSICAL_CONTEXT", 0.0)
    );
    private static final String SELECTION_TYPE = "";
    private static final String MATING_POOL_SELECTION_TYPE = "";
    private static final int NUMBER_OF_GENERATIONS = 100;
    private static final int NUMBER_OF_ITERATIONS = 10;

    private static final List<String> CRITERIA = List.of("DESCENDING_MELODY_LINE", "ASCENDING_MELODY_LINE");

    private static final HashMap<String,Pair<Double, Double>> CRITERIA_RANGES = new HashMap<>()
    {{
        put("ASCENDING_MELODY_LINE", new ImmutablePair<>(0.0,1.0));
        put("DESCENDING_MELODY_LINE", new ImmutablePair<>(0.0,1.0));
    }};

    private static final Pair<Boolean, Integer> SAVE_TO_JSON = new ImmutablePair<>(true, 1);
    private static final boolean PLAY = false;

    public static void main(String[] args) {

        for (int i = 0; i< CHORD_PROGRESSION.size(); i++){
            System.out.println("\nprogression: " + CHORD_PROGRESSION.get(i) + ", key: " + MELODY_KEY.get(i));
            runAlgorithm(new ImmutablePair<>(CHORD_PROGRESSION.get(i), MELODY_KEY.get(i)));
        }

    }

    public static void runAlgorithm(Pair<List<String>, Pair<String, String>> parameters) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH:mm:ss");
        String folderName = dtf.format(now);


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
