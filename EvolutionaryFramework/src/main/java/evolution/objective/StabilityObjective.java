package evolution.objective;

import evolution.music.Representation;
import evolution.solution.Individual;
import evolution.util.Util;
import lombok.var;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StabilityObjective extends Objective{

    final String name = "STABILITY";

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

        var melodyKeyVal = Representation.NotesMap.get(melodyKey.getLeft());
        //CHORD NOTES
        int count = 1;
        int lastNoteValue = 0;
        int noteValue;
        for (int i = 0; i < melody.size(); i++) {
            for (int j = 0; j < melody.get(i).size(); j++) {
                noteValue = melody.get(i).get(j);
                if (noteValue != 0 && noteValue != -1) {
                    if (chrProgPattern.get(0).get(chrProg.get(i)).contains((noteValue - melodyKeyVal) % 12)) {
                        fitness += 30;
                        if (lastNoteValue != 0) {
                            if (Math.abs(noteValue - lastNoteValue) <= 2) {
                                fitness += 10;
                            }
                        }
                    } else if (chrProgPattern.get(1).get(chrProg.get(i)).contains((noteValue - melodyKeyVal) % 12)) {
                        fitness -= 10;
                    } else if (chrProgPattern.get(2).get(chrProg.get(i)).contains((noteValue - melodyKeyVal) % 12)) {
                        fitness -= 20;
                    } else {
                        fitness -= 30;
                    }
                    count = 1;
                    lastNoteValue = noteValue;
                } else if (noteValue == 0) {
                    count += 1;
                } else {
                    count = 1;
                }
            }
        }

        // RHYTHM
//        List<List<Integer>> durations = new ArrayList<>();
//        int lengthOfNote = 1;
//        int lastNote = 1000;
//        for (int i = 0; i < melody.size(); i++) {
//            List<Integer> barDurations = new ArrayList<>();
//            for (int j = 0; j < melody.get(i).size(); j++) {
//                noteValue = melody.get(i).get(j);
//                if (noteValue == 0){
//                    lengthOfNote += 1;
//                }
//                if (noteValue != 0){
//                    if (lengthOfNote != 0) {
//                        barDurations.add(lengthOfNote);
//                        lengthOfNote = 1;
//                        lastNote = noteValue;
//                    } else if (lastNote != 1000){
//                        barDurations.add(1);
//                        lastNote = noteValue;
//                    }
//                }
//            }
//            durations.add(barDurations);
//        }
//
//        fitness /= Util.flattenListOfListsStream(durations).size();


        // MOTION
        List<Integer> melodyArray = Util.flattenListOfListsStream(melody);
        melodyArray.removeAll(List.of(-1, 0));
        int countStepwiseAsc = 0;
        int countStepwiseDesc = 0;
        int descNote = 1000;
        int ascNote = 0;
        int countStepwiseLeapDesc = 0;
        int countStepwiseLeapAsc = 0;
        boolean countLeapDesc = false;
        boolean countLeapAsc = false;
        for (int i = 1; i < melodyArray.size(); i++) {
            if (melodyArray.get(i - 1) - melodyArray.get(i) <= 4 && melodyArray.get(i - 1) - melodyArray.get(i) > 0 && descNote > melodyArray.get(i)) {
                descNote = melodyArray.get(i);
                if (countLeapDesc){
                    countStepwiseLeapDesc += 1;
                } else {
                    countStepwiseDesc += 1;
                }
            } else if (melodyArray.get(i - 1) - melodyArray.get(i) >= -4 && melodyArray.get(i - 1) - melodyArray.get(i) < 0 && ascNote < melodyArray.get(i)) {
                ascNote = melodyArray.get(i);
                if (countLeapAsc){
                    countStepwiseLeapAsc += 1;
                } else {
                    countStepwiseAsc += 1;
                }
            } else if (melodyArray.get(i - 1) - melodyArray.get(i) > 4 && descNote > melodyArray.get(i)) {
                descNote = melodyArray.get(i);
                countLeapDesc = true;
                if (countStepwiseDesc < 5) {
                    countStepwiseLeapDesc = 0;
                }
            } else if (melodyArray.get(i - 1) - melodyArray.get(i) < -4 && ascNote < melodyArray.get(i)){
                ascNote = melodyArray.get(i);
                countLeapAsc = true;
                if (countStepwiseAsc < 5){
                    countStepwiseAsc = 0;
                }
            }
        }
        if (countStepwiseLeapDesc > 5 || countStepwiseLeapAsc > 5){
            fitness += 20;
        } else if (countStepwiseDesc >= countStepwiseAsc){
            fitness += 20;
        } else {
            fitness += 15;
        }


        //INTERVAL
        List<Integer> perfectIntervals = List.of(0, 12, 5, 7);
        for (int i = 1; i < melodyArray.size(); i++) {
            if (perfectIntervals.contains(Math.abs(melodyArray.get(i - 1) - melodyArray.get(i)))) {
                fitness += 10;
            } else if (Math.abs(melodyArray.get(i - 1) - melodyArray.get(i)) > 12) {
                fitness -= 20;
            }
        }

        return fitness;

    }
}
