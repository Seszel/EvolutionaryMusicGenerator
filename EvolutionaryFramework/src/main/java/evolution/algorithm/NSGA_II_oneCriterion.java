package evolution.algorithm;

import com.google.common.collect.ImmutableList;
import evolution.music.PlayResultMelodies;
import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.operator.MatingPoolSelection;
import evolution.population.PopulationNSGA_II;
import evolution.stats.StatsNSGA_II_oneCriterion;
import lombok.var;
import me.tongfei.progressbar.ProgressBar;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NSGA_II_oneCriterion extends AEvolutionaryAlgorithm {


    public NSGA_II_oneCriterion(int popSize, int numberOfBars, int maxNumberOfNotes,
                   String representationType, List<String> chordProgression,
                   Pair<String, String> melodyKey, String crossoverType, Pair<String, Double> mutationType,
                   String selectionType, String matingPoolSelectionType,
                   int numberOfGenerations, int numberOfIteration,
                   List<String> criteria, HashMap<String,Pair<Double, Double>> criteriaRanges,
                   Pair<Boolean, Integer> saveToJSON, String folderName, boolean play) {

        super(popSize, numberOfBars, maxNumberOfNotes,
                representationType, chordProgression, melodyKey,
                crossoverType, mutationType, selectionType,
                matingPoolSelectionType, numberOfGenerations, numberOfIteration,
                criteria, criteriaRanges, saveToJSON, folderName, play);
    }

    @Override
    public void run() {

        StatsNSGA_II_oneCriterion stats = new StatsNSGA_II_oneCriterion("NSGA-II one criterion", popSize,
                numberOfBars, maxNumberOfNotes, representationType,
                chordProgression, melodyKey, crossoverType,
                mutationType, selectionType, matingPoolSelectionType,
                numberOfGenerations, criteria, folderName);

        System.out.println("Algorithm NSGA_II for " + criteria.get(0) + " criterion is working, iteration: " + (numberOfIteration + 1));

        var params = new EvaluationParameters("JoannaParameters");
        params.addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION_PATTERN,
                        Representation.ChordProgression(melodyKey.getRight()))
                .addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION,
                        chordProgression)
                .addParam(EvaluationParameters.ParamName.MELODY_KEY,
                        melodyKey)
                .addParam(EvaluationParameters.ParamName.CRITERIA_RANGES,
                        criteriaRanges);

        PopulationNSGA_II population = new PopulationNSGA_II(
                popSize, representationType, criteria,
                numberOfBars, maxNumberOfNotes,
                chordProgression, melodyKey, params
        );
        ImmutableList<Integer> representation = Representation.getReprInt(representationType);

        population.generatePopulation(representation);

        List<Integer> generations = IntStream.rangeClosed(1, numberOfGenerations)
                .boxed().collect(Collectors.toList());

        for (Integer g : ProgressBar.wrap(generations, "Iteration: " + (numberOfIteration + 1))) {

            var matingPool = MatingPoolSelection.tournament((int)(0.5*popSize), popSize, population
            );
            population.createOffsprings(matingPool, representation, getCrossoverType(), getMutationType());

            population.changePopulation();

            // max
            population.getPopulation().sort(Comparator.comparing(o -> o.getFitnessByName(criteria.get(0)), Comparator.reverseOrder()));
            //min
//            population.getPopulation().sort(Comparator.comparing(o -> o.getFitnessByName(criteria.get(0))));

            population.setPopulation(
                    new ArrayList<>(population.getPopulation().subList(0, popSize))
            );

            if (saveToJSON.getLeft() && (g % saveToJSON.getRight() == 1 || g == numberOfGenerations)) {
                stats.updateStats(g, population.getPopulation());
            }

        }

        if (saveToJSON.getLeft()) {
            stats.generateJSON(numberOfIteration);
        }

        if (play){
            PlayResultMelodies.playMelodies(population.getPopulation().subList(0,1), melodyKey, chordProgression);
        }

        System.out.println("Nsga_II algorithm for one criterion ended work, iteration: " + (numberOfIteration + 1));


    }

}
