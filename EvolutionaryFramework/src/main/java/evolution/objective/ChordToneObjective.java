package evolution.objective;

import evolution.music.Representation;
import evolution.solution.Individual;
import evolution.util.Util;
import lombok.var;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChordToneObjective extends Objective {

    static final String name = "CHORD_TONE";


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



        int count = 0;
        int noteValue;
        double toFitness = 0;
        for (int i = 0; i < melody.size(); i++) {
            for (int j = 0; j < melody.get(i).size(); j++) {
                noteValue = melody.get(i).get(j);
                if (noteValue != 0) {
                    if (count != 0){
                        fitness += toFitness*((double)count/melody.get(i).size());
                        toFitness = 0;
                    }
                    if (chrProgPattern.get(0).get(chrProg.get(i)).contains(Math.abs(noteValue - melodyKeyVal) % 12)) {
                        toFitness += 1;
                    }
                    count = 1;
                } else {
                    count += 1;
                }
            }
        }
        fitness /= melody.size();

        double min = criteriaRanges.get(name).getLeft();
        double max = criteriaRanges.get(name).getRight();
        return ( fitness - min ) / ( max - min );

//        return fitness;

    }

}
