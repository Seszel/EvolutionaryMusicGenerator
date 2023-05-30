package evolution.objective.subcriterion;

import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.objective.Objective;
import evolution.solution.Individual;
import evolution.util.Util;
import lombok.var;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerfectIntervalObjective extends Objective {

    static final String name = "PERFECT_INTERVAL";


    public static Double evaluate(Individual individual, EvaluationParameters pack) {

        double fitness = 0;

        List<List<Integer>> melody = individual.getGenome().getMelody();

        @SuppressWarnings("unchecked")
        var chrProgPattern = (ArrayList<HashMap<String, List<Integer>>>) pack.parameters
                .get(EvaluationParameters.ParamName.CHORD_PROGRESSION_PATTERN);
        @SuppressWarnings("unchecked")
        var chrProg = (List<String>) pack.parameters
                .get(EvaluationParameters.ParamName.CHORD_PROGRESSION);
        @SuppressWarnings("unchecked")
        var melodyKey = (Pair<String, String>) pack.parameters
                .get(EvaluationParameters.ParamName.MELODY_KEY);
        @SuppressWarnings("unchecked")
        var criteriaRanges = (HashMap<String, Pair<Double, Double>>) pack.parameters
                .get(EvaluationParameters.ParamName.CRITERIA_RANGES);

        var melodyKeyVal = Representation.NotesMap.get(melodyKey.getLeft());


        List<Integer> melodyArray = Util.flattenListOfListsStream(melody);
        melodyArray.removeAll(List.of(-1, 0));

        List<Integer> perfectConsonances = List.of(0, 12, 5, 7);
        int interval;
        for (int i = 1; i < melodyArray.size(); i++) {
            interval = Math.abs(melodyArray.get(i - 1) - melodyArray.get(i));
            if (perfectConsonances.contains(interval)) {
                fitness += 1.0/melodyArray.size();
            }
        }


        double min = criteriaRanges.get(name).getLeft();
        double max = criteriaRanges.get(name).getRight();
        return ( fitness - min ) / ( max - min );

//        return fitness;

    }
}
