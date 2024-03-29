package evolution.objective.subcriterion;

import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.objective.Objective;
import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChordToneObjective extends Objective {

    static final String name = "CHORD_TONE";


    public static Pair<Double, Double> evaluate(Individual individual, EvaluationParameters pack) {

        double fitness = 0;
        double penalty = 0;

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


        int[] countNoPauses = new int[4];
        int noteValue;
        for (int i = 0; i < melody.size(); i++) {
            int j = 0;
            while (j < melody.get(i).size()){
                noteValue = melody.get(i).get(j);
                if (noteValue != -1 && noteValue != 0){
                    countNoPauses[i]++;
                    for (int k=j+1; k<melody.get(i).size(); k++){
                        if (melody.get(i).get(k) == 0){
                            countNoPauses[i]++;
                        }
                        else {
                            j = k-1;
                            break;
                        }
                    }
                }
                j++;
            }
        }

        int count = 0;
        double toFitness = 0;
        for (int i = 0; i < melody.size(); i++) {
            for (int j = 0; j < melody.get(i).size(); j++) {
                noteValue = melody.get(i).get(j);
                if (noteValue != 0) {
                    if (count != 0){
                        fitness += toFitness * ((double)count/countNoPauses[i]);
                        toFitness = 0;
                    }
                    if (noteValue != -1 && chrProgPattern.get(0).get(chrProg.get(i)).contains(Math.abs(noteValue - melodyKeyVal) % 12)) {
                        toFitness += 1;
                    }
                    count = 1;
                } else {
                    count += 1;
                }
                if (j == (melody.get(i).size()-1)){
                    fitness += toFitness * ((double)count/countNoPauses[i]);
                    toFitness = 0;
                    count = 0;
                }
            }
        }
        fitness /= melody.size();

        double min = criteriaRanges.get(name).getLeft();
        double max = criteriaRanges.get(name).getRight();
        return new ImmutablePair<>(( fitness - min ) / ( max - min ), penalty);

//        return new ImmutablePair<>(fitness, penalty);
    }

}
