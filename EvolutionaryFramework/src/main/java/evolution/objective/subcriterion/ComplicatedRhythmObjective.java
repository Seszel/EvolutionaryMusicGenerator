package evolution.objective.subcriterion;

import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.objective.Objective;
import evolution.solution.Individual;

import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ComplicatedRhythmObjective extends Objective {

    static final String name = "COMPLICATED_RHYTHM";


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

        List<Integer> melodyArray = Util.flattenListOfListsStream(melody);

        List<Integer> strongBeatsIdx = IntStream.range(0, melodyArray.size())
                .filter(i -> i % 4 == 2)
                .boxed()
                .collect(Collectors.toList());

        double fitnessBeat = 0;

        for (int beat : strongBeatsIdx){
            if (melodyArray.get(beat) != 0){
                fitnessBeat += 1;
            }
        }

        fitnessBeat /= strongBeatsIdx.size();

        int noteValue;
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

        List<Integer> durationsArray = Util.flattenListOfListsStream(durations);

        int leftNote, rightNote, note;
        boolean left;
        double fitnessDuration = 0;
        for (int i=1; i<durationsArray.size()-1; i++){

            left = false;
            leftNote = durationsArray.get(i-1);
            note = durationsArray.get(i);
            rightNote = durationsArray.get(i+1);

            if (leftNote >= note){
                if ((double)leftNote / note >= 2){
                    left = true;
                }
            } else {
                if ((double)note / leftNote >= 2){
                    left = true;
                }
            }
            if (left){
                if (rightNote >= note){
                    if ((double)rightNote / note >= 2){
                        fitnessDuration += 1;
                    }
                } else {
                    if ((double)note / rightNote >= 2){
                        fitnessDuration += 1;
                    }
                }
            }
        }

        fitnessDuration /= durationsArray.size()-2;

        double fitnessNumberOfNotes = 0;
        if(durationsArray.size() >= 0.5 * melodyArray.size()){
            fitnessNumberOfNotes +=1;
        }

        double fitnessSameRhythm = 0;
        int countSimilarPossibilities = 0;
        for (int i=0; i< durations.size(); i++){
            for (int k=i+1; k< durations.size(); k++){
                countSimilarPossibilities += 1;
                if (durations.get(i).equals(durations.get(k))){
                    fitnessSameRhythm += 1;
                }
            }
        }
        fitnessSameRhythm = fitnessSameRhythm / countSimilarPossibilities;

        fitness += (3*fitnessBeat + 2*fitnessDuration + 0.0*fitnessNumberOfNotes + fitnessSameRhythm)/6.0;

        double min = criteriaRanges.get(name).getLeft();
        double max = criteriaRanges.get(name).getRight();
        return new ImmutablePair<>(( fitness - min ) / ( max - min ), penalty);

//        return new ImmutablePair<>(fitness, penalty);
    }

}
