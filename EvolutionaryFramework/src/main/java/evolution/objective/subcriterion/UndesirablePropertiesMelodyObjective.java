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

    public static List<Integer> getChordNotes(int idx, int numberOfNotes,
                                       ArrayList<HashMap<String, List<Integer>>> chrProgPattern, List<String> chrProg) {
        return chrProgPattern.get(0).get(chrProg.get((idx/numberOfNotes)));
    }

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

        //*********************************************************************************************************************//

        double MNC = 0;

        // Rule 1 ////////////////////////////////////////////////////////////////////////////////////////

        double UT = 0;
        double AN = 0;
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


        for (int i=0; i < strongBeatsIdx.size(); i++){
            if (i%(numberOfNotes/4) == 0){
                numberOfCurrentBar += 1;
            }

            ///// AN
            if (i == strongBeatsIdx.size() - 1) {
                idx = strongBeatsIdx.get(i);
                noteValue = melodyArray.get(idx);
                chordNotes = chrProgPattern.get(0).get(chrProg.get(numberOfCurrentBar));
                if (noteValue != 0 && noteValue != -1) {
                    if (!chordNotes.contains(Math.abs(noteValue - melodyKeyVal) % 12)) {
                        if (chordNotes.contains(Math.abs((noteValue - 1) - melodyKeyVal) % 12)) {
                            AN += 1;
                        }
                    }

                }
                break;
            }
            ////////

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
                    // AN
                    if( chordNotes.contains(Math.abs( (noteValue - 1) - melodyKeyVal) % 12)) {
                        AN += 1;
                    }
                    ///////
                }
            }
            UT += tempValue;
        }

        MNC += UT/(strongBeatsIdx.size()-1);


        // Rule 2 ////////////////////////////////////////////////////////////////

        double NCC = 0;
        numberOfCurrentBar = -1;
        boolean skippedFirstNote = false;
        int numberOfTotalNotes = numberOfBars*numberOfNotes;
        int iNote = 0, iNoteRight = 0, iNoteLeft = 0, note = 0, noteRight = 0, noteLeft = 0;
        boolean shouldBreak = false;
        int tempValue2, numberOfSounds = 0;
        List<Integer> chordNotesLeft, chordNotesRight;


        for (int i=0; i<numberOfTotalNotes; i++){
            if (i%(numberOfNotes) == 0){
                numberOfCurrentBar += 1;
            }

            tempValue = 0;
            tempValue2 = 0;

            if (melodyArray.get(i) != 0 && melodyArray.get(i) != -1){
                numberOfSounds += 1;
                if (!skippedFirstNote){
                    skippedFirstNote = true;
                    continue;
                }
                iNote = i;
                note = melodyArray.get(i);
                for (int r=i+1; r<numberOfTotalNotes; r++){
                    if (melodyArray.get(r) != 0 && melodyArray.get(r) != -1){
                        iNoteRight = r;
                        noteRight = melodyArray.get(r);
                        break;
                    }
                    if (r == numberOfTotalNotes - 1){
                        shouldBreak = true;
                    }
                }
                if (shouldBreak){
                    break;
                }
                for (int l=i-1; l>=0; l--){
                    if (melodyArray.get(l) != 0 && melodyArray.get(l) != -1){
                        iNoteLeft = l;
                        noteLeft = melodyArray.get(l);
                        break;
                    }
                }
                chordNotes = getChordNotes(iNote, numberOfNotes, chrProgPattern, chrProg);
                chordNotesLeft = getChordNotes(iNoteLeft, numberOfNotes, chrProgPattern, chrProg);
                chordNotesRight = getChordNotes(iNoteRight, numberOfNotes, chrProgPattern, chrProg);

                if (!chordNotes.contains(Math.abs(note - melodyKeyVal) % 12)){
                    if (!chordNotesLeft.contains(Math.abs(noteLeft - melodyKeyVal) % 12)) {
                        tempValue += 1;
                    }
                    if ( Math.abs(noteLeft - note) - 2 >= 0 ) {
                        tempValue += 1;
                    }
                    if (!chordNotesRight.contains(Math.abs(noteRight - melodyKeyVal) % 12)) {
                        tempValue2 += 1;
                    }
                    if ( Math.abs(note - noteRight) - 2 >= 0 ) {
                        tempValue2 += 1;
                    }
                }
                NCC += tempValue*tempValue2;

            }
        }

        MNC +=  NCC / (numberOfSounds) ;

        // Rule 3 ////////////////////////////////////////////////////////////////

        List<List<Integer>> updatedMelody = melody.stream()
                .map(nestedList -> nestedList.stream()
                        .filter(n -> n != 0 && n != -1)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        int intervalLeft, intervalRight;
        double NCL = 0;
        skippedFirstNote = false;
        numberOfSounds = 0;
        int countChordNotes, countNotes;
        double ON = 0;

        for (int i=0; i<updatedMelody.size(); i++){
            countNotes = 0; ///ON
            countChordNotes = 0; //ON
            for (int j=0; j<updatedMelody.get(i).size(); j++){
                countNotes += 1; /// ON

                if (!skippedFirstNote){
                    skippedFirstNote = true;
                    continue;
                }
                if (i == updatedMelody.size()-1 &&  j == updatedMelody.get(i).size()-1){
                    break;
                }
                numberOfSounds += 1;
                if (!chrProgPattern.get(0).get(chrProg.get((i))).contains(Math.abs(updatedMelody.get(i).get(j) - melodyKeyVal) % 12) ){
                    if (j-1 < 0){
                        intervalLeft = Math.abs(updatedMelody.get(i-1).get(updatedMelody.get(i-1).size()-1) - updatedMelody.get(i).get(j));
                    } else {
                        intervalLeft = Math.abs(updatedMelody.get(i).get(j - 1) - updatedMelody.get(i).get(j));
                    }
                    if (j+1 > updatedMelody.get(i).size()-1){
                        intervalRight = Math.abs(updatedMelody.get(i).get(j) - updatedMelody.get(i+1).get(0));
                    } else {
                        intervalRight = Math.abs(updatedMelody.get(i).get(j) - updatedMelody.get(i).get(j+1));
                    }

                    if (4 - intervalLeft >=0 ){
                        NCL += 1;
                    }
                    if (4 - intervalRight >=0 ){
                        NCL += 1;
                    }
                }
                //// ON
                else {
                    countChordNotes += 1;
                }
                //////
            }
            /// ON
            if (0.5 - ((double)countChordNotes/countNotes) >= 0){
                ON += 1;
            }
            ////
        }

        MNC += NCL / numberOfSounds;


        fitness += MNC / 8 ;

        //*********************************************************************************************************************//

        // in rule 1
        fitness += AN / strongBeatsIdx.size();

        //*********************************************************************************************************************//
        // in rule 3

        fitness += ON / melody.size();

        //*********************************************************************************************************************//


        double BL = 0;

        List<Integer> updatedMelodyArray = melodyArray.stream()
                .filter(n -> n != -1 && n != 0)
                .collect(Collectors.toList());

        for (int i=0; i<updatedMelodyArray.size()-1; i++){
            noteValue = updatedMelodyArray.get(i);
            nextNoteValue = updatedMelodyArray.get(i+1);
            if (16 - Math.abs(noteValue - nextNoteValue) < 0){
                BL += 1;
            }
        }

        fitness += BL/(updatedMelodyArray.size() - 1);


        return  fitness ;


    }


}