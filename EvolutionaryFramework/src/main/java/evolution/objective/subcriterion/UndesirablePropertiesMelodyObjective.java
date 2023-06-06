package evolution.objective.subcriterion;

import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.objective.Objective;
import evolution.solution.Individual;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UndesirablePropertiesMelodyObjective extends Objective {

    static final String name = "UNDESIRABLE_PROPERTIES_MELODY";


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


        // Rule 1

        double UT = 0;
        int noteValue, nextNoteValue;

        int numberOfBars = melody.size();
        int numberOfNotes = melody.get(0).size();
        int numberOfCurrentBar = -1;
        int idx, idx1, tempValue;

        List<Integer> melodyArray = Util.flattenListOfListsStream(melody);

        List<Integer> strongBeatsIdx = IntStream.range(0, melodyArray.size())
                .filter(i -> i % 4 == 0)
                .boxed()
                .collect(Collectors.toList());

        List<Integer> chordNotes, chordNotes2;


        for (int i=0; i<strongBeatsIdx.size() - 1; i++){
            if (i%(numberOfNotes/4) == 0){
                numberOfCurrentBar += 1;
            }
            idx = strongBeatsIdx.get(i);
            noteValue = melodyArray.get(idx);
            idx1 = strongBeatsIdx.get(i+1);
            nextNoteValue = melodyArray.get(idx1);

            chordNotes = chrProgPattern.get(0).get(chrProg.get(numberOfCurrentBar));
            if ( idx1 % numberOfNotes == 0 ){
                chordNotes2 = chrProgPattern.get(0).get(chrProg.get(numberOfCurrentBar+1));
            } else {
                chordNotes2 = chordNotes;
            }

            tempValue = 0;
            if (noteValue != 0 && noteValue != -1){
                if (!chordNotes.contains(Math.abs(noteValue - melodyKeyVal) % 12)){
                    if (nextNoteValue != 0 && nextNoteValue != -1) {
                        if (!chordNotes2.contains(Math.abs(nextNoteValue - melodyKeyVal) % 12)) {
                            tempValue += 1;
                        }
                        if ( Math.abs(nextNoteValue - noteValue) - 2 >= 0 ) {
                            tempValue += 1;
                        }
                    }
                }
            }
            UT += tempValue;
        }

        fitness += UT/(2*strongBeatsIdx.size());


        // Rule2








        return - fitness ;


    }

}