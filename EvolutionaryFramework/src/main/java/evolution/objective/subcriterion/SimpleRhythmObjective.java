package evolution.objective.subcriterion;

import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.objective.Objective;
import evolution.solution.Individual;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleRhythmObjective extends Objective {


    static final String name = "SIMPLE_RHYTHM";


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

        int noteValue;
        double fitnessBeat = 0;
        for (int i=0; i<melody.size(); i++){
            for (int j=0; j<melody.get(i).size(); j += melody.get(i).size()/4 ){
                noteValue = melody.get(i).get(j);
                if (noteValue != 0 && noteValue != -1){
                    fitnessBeat += 1;
                }
            }
        }

        fitnessBeat /= melody.size()*(melody.get(0).size()/4.0);



        List<List<Integer>> durations = new ArrayList<>();
        int lengthOfNote;


        for (int i = 0; i < melody.size(); i++) {
            lengthOfNote = 0;
            List<Integer> barDurations = new ArrayList<>();
            for (int j = 0; j < melody.get(i).size(); j++) {
                noteValue = melody.get(i).get(j);
                if (noteValue == 0){
                    lengthOfNote += 1;
                }
                if (noteValue != 0){
                    if (lengthOfNote != 0) {
                        barDurations.add(lengthOfNote);
                        lengthOfNote = 1;
                    } else {
                        lengthOfNote += 1;
                    }
                    if (j==melody.get(i).size()-1){
                        barDurations.add(1);
                    }
                }

            }
            durations.add(barDurations);
        }

        double fitnessSameRhythm = 0;
        double fitnessDuration = 0;
        int countSimilarPossibilities = 0;
        List<Integer> powersOfTwo = IntStream.rangeClosed(2, (int)Math.sqrt(melody.get(0).size()))
                .mapToObj(i -> (int) Math.pow(2, i))
                .collect(Collectors.toList());
        for (int i=0; i<durations.size(); i++){
            for (int j=1; j<durations.get(i).size(); j++) {
                if (powersOfTwo.contains(durations.get(i).get(j))){
                    fitnessDuration += 1.0*durations.get(i).get(j)/melody.get(i).size();
                }
            }
            for (int k=i+1; k< durations.size(); k++){
                countSimilarPossibilities += 1;
                if (durations.get(i).equals(durations.get(k))){
                    fitnessSameRhythm += 1;
                }
            }
        }
        fitnessDuration /= durations.size();
        fitnessSameRhythm = fitnessSameRhythm / countSimilarPossibilities;


        fitness = (1.0*fitnessBeat + 0.75*fitnessDuration + 0.5*fitnessSameRhythm) / 2.25;

        double min = criteriaRanges.get(name).getLeft();
        double max = criteriaRanges.get(name).getRight();
//        return ( fitness - min ) / ( max - min );

        fitness += UndesirablePropertiesMelodyObjective.evaluate(individual, pack);

        return fitness;

    }


}
