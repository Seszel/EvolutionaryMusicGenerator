package evolution.objective;

import evolution.music.Representation;
import evolution.solution.Individual;
import evolution.util.Util;
import lombok.var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TensionObjective extends Objective{

    final String name = "TENSION";

    public static Double evaluate(Individual individual, EvaluationParameters pack) {

        ArrayList<ArrayList<Integer>> melody = individual.getGenome().getMelody();
        double fitness = 0;
        @SuppressWarnings("unchecked")
        var chProgPattern = (ArrayList<HashMap<String, List<Integer>>>) pack.parameters
                .get(EvaluationParameters.ParamName.CHORD_PROGRESSION_PATTERN);
        @SuppressWarnings("unchecked")
        var chProg = (List<String>) pack.parameters
                .get(EvaluationParameters.ParamName.CHORD_PROGRESSION);
        var melodyKey = (String) pack.parameters
                .get(EvaluationParameters.ParamName.MELODY_KEY);

        var melodyKeyVal = Representation.NotesMap.get(melodyKey);


        //CHORD NOTES
        int count = 1;
        int lastNoteValue = 0;
        int noteValue;
        for (int i = 0; i < melody.size(); i++) {
            for (int j = 0; j < melody.get(i).size(); j++) {
                noteValue = melody.get(i).get(j);
                if (noteValue != 0 && noteValue != -1) {
                    if (chProgPattern.get(0).get(chProg.get(i)).contains((noteValue - melodyKeyVal) % 12)) {
                        fitness -= 5;
                        if (lastNoteValue != 0) {
                            if (Math.abs(noteValue - lastNoteValue) <= 2) {
                                fitness += 30;
                            }
                        }
                    } else if (chProgPattern.get(1).get(chProg.get(i)).contains((noteValue - melodyKeyVal) % 12)) {
                        fitness += 20;
                    } else if (chProgPattern.get(2).get(chProg.get(i)).contains((noteValue - melodyKeyVal) % 12)) {
                        fitness += 5;
                    } else {
                        fitness -= 10;
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

        // MOTION
        ArrayList<Integer> melodyArray = Util.flattenListOfListsStream(melody);
        melodyArray.removeAll(List.of(-1, 0));
        int countStepwise = 0;
        int countLeap = -1;
        for (int i = 1; i < melodyArray.size(); i++) {
            if (Math.abs(melodyArray.get(i - 1) - melodyArray.get(i)) <= 4) {
                countStepwise += 1;
            } else {
                if (countLeap > -1) {
                    if (countStepwise > countLeap) {
                        countLeap = countStepwise;
                    }
                } else {
                    countLeap = 0;
                }
                countStepwise = 0;
            }
        }
        if (countStepwise == melodyArray.size()) {
            if (melodyArray.get(0) > melodyArray.get(melodyArray.size() - 1)) {
                fitness += 15;
            } else if (melodyArray.get(0) < melodyArray.get(melodyArray.size() - 1)) {
                fitness += 20;
            }
        }
        else if (countLeap > 1 && Math.abs(melodyArray.get(0) - melodyArray.get(melodyArray.size() - 1)) > 0) {
            fitness += 20;
        }

        //INTERVAL
        List<Integer> perfectIntervals = List.of(0, 12, 5, 7);
        for (int i = 1; i < melodyArray.size(); i++) {
            if (perfectIntervals.contains(Math.abs(melodyArray.get(i - 1) - melodyArray.get(i)))) {
                fitness -= 5;
            } else if (Math.abs(melodyArray.get(i - 1) - melodyArray.get(i)) > 12) {
                fitness -= 20;
            }
        }

//         punishment for to much of notes
        for (Integer integer : melodyArray) {
            if (integer != 0) {
                fitness -= 15;
            } else {
                fitness += 5;
            }
        }

        return fitness;

    }
}
