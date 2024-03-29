package evolution.operator;

import com.google.common.collect.ImmutableList;
import evolution.music.Genome;
import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Mutation {


    public static Genome mutation(double mutationProbability, List<Pair<String, Double>> mutationType, Genome genome,
                                  ImmutableList<Integer> representation, int generationNumber, EvaluationParameters evalParams) {

        @SuppressWarnings("unchecked")
        var chrProgPattern = (ArrayList<HashMap<String, List<Integer>>>) evalParams.parameters
                .get(EvaluationParameters.ParamName.CHORD_PROGRESSION_PATTERN);
        @SuppressWarnings("unchecked")
        var chrProg = (List<String>) evalParams.parameters
                .get(EvaluationParameters.ParamName.CHORD_PROGRESSION);
        @SuppressWarnings("unchecked")
        var melodyKey = (Pair<String, String>) evalParams.parameters
                .get(EvaluationParameters.ParamName.MELODY_KEY);

        var melodyKeyVal = Representation.NotesMap.get(melodyKey.getLeft());

        Random randomObj = new Random();

        double maxDouble = mutationType.stream()
                .mapToDouble(Pair::getValue)
                .max()
                .orElse(Double.MIN_VALUE);


        List<Pair<String, Double>> mutableList = new ArrayList<>(mutationType);

        mutableList.sort(Comparator.comparing(Pair::getValue));
        mutableList.replaceAll(pair -> new ImmutablePair<>(pair.getKey(), pair.getValue() / maxDouble));

        double randomProbability = randomObj.nextDouble();
        String mutationName = "";
        for (int i=0; i<mutableList.size(); i++){
            if (randomProbability < mutableList.get(i).getRight()){
                mutationName = mutableList
                        .subList(i, mutableList.size())
                        .get(new Random().nextInt(mutationType.size()-i))
                        .getLeft();
                break;
            }
        }

        double randomMutationProbability = randomObj.nextDouble();
        if (randomMutationProbability > mutationProbability){
            mutationName = "NO_MUTATION";
        }

        switch (mutationName) {
            case "SIMPLE":
                return simpleMutation(genome, representation);
            case "BAR_ORDER":
                return barOrderMutation(genome);
            case "ADD_ZERO":
                return addZeroMutation(genome);
            case "ADD_REST":
                return addRestMutation(genome);
            case "SWAP_NOTES":
                return swapNotes(genome);
            case "SWAP_DURATION":
                return swapDuration(genome);
            case "TRANSPOSE_NOTES":
                return transposeNotes(genome);
            case "MUSICAL_CONTEXT":
                return musicalContextMutation(genome, representation, chrProgPattern, chrProg, melodyKeyVal);
            case "NO_MUTATION":
            default:
                return genome;
        }
    }

    public static Genome addZeroMutation(Genome genome){
        for (int i = 0; i < genome.getMelody().size(); i++) {
            int idx = Util.getRandomNumber(0, genome.getMelody().get(i).size() - 1);
            genome.getMelody().get(i).set(idx, 0);
        }
        return genome;
    }

    public static Genome addRestMutation(Genome genome){
        for (int i = 0; i < genome.getMelody().size(); i++) {
            int idx = Util.getRandomNumber(0, genome.getMelody().get(i).size() - 1);
            genome.getMelody().get(i).set(idx, -1);
        }
        return genome;
    }

    public static Genome swapDuration(Genome genome) {
        int noteValue;
        List<List<Integer>> durations = new ArrayList<>();
        int lengthOfNote;

        for (int i = 0; i < genome.getMelody().size(); i++) {
            lengthOfNote = 0;
            List<Integer> barDurations = new ArrayList<>();
            for (int j = 0; j < genome.getMelody().get(i).size(); j++) {
                noteValue = genome.getMelody().get(i).get(j);
                if (noteValue == 0) {
                    lengthOfNote += 1;
                }
                if (noteValue != 0) {
                    if (lengthOfNote != 0) {
                        barDurations.add(lengthOfNote);
                        lengthOfNote = 1;
                    } else {
                        lengthOfNote += 1;
                    }
                    if (j == genome.getMelody().get(i).size() - 1) {
                        barDurations.add(1);
                    }
                }

            }
            durations.add(barDurations);
        }

        for (int i = 0; i < genome.getMelody().size(); i++) {
            if (durations.get(i).size() > 1) {
                int idx = Util.getRandomNumber(0, durations.get(i).size() - 2);
                int realIdx = 0;
                for (int num : durations.get(i).subList(0, idx)) {
                    realIdx += num;
                }
                int note1 = genome.getMelody().get(i).get(realIdx + durations.get(i).get(idx));
                int note2 = genome.getMelody().get(i).get(realIdx + durations.get(i).get(idx + 1));
                genome.getMelody().get(i).set(realIdx + durations.get(i).get(idx + 1), note1);
                genome.getMelody().get(i).set(realIdx + durations.get(i).get(idx), note2);
            }
        }
        return genome;
    }

    public static Genome swapNotes(Genome genome){
        for (int i = 0; i < genome.getMelody().size(); i++) {
                int idx = Util.getRandomNumber(2, genome.getMelody().get(i).size() - 1);
                int note1 = genome.getMelody().get(i).get(idx);
                int note2 = genome.getMelody().get(i).get(idx - 1);
                genome.getMelody().get(i).set(idx, note2);
                genome.getMelody().get(i).set(idx - 1, note1);
            }
        return genome;
    }

    public static Genome transposeNotes(Genome genome){
        for (int i = 0; i < genome.getMelody().size(); i++) {
            double median = genome.getMelody().get(i).stream()
                    .filter(n -> n != 0 && n != -1)
                    .sorted()
                    .skip(genome.getMelody().get(i).stream().filter(n -> n != 0 && n != -1).count() / 2 - (genome.getMelody().get(i).stream().filter(n -> n != 0 && n != -1).count() % 2 == 0 ? 1 : 0))
                    .limit(genome.getMelody().get(i).stream().filter(n -> n != 0 && n != -1).count() % 2 == 0 ? 2 : 1)
                    .mapToDouble(Integer::doubleValue)
                    .average()
                    .orElse(Double.NaN);
            int idx = Util.getRandomNumber(1, genome.getMelody().get(i).size() - 1);
            int note1 = genome.getMelody().get(i).get(idx);
            if (note1 == 0 || note1 == -1) {
                do {
                    idx -= 1;
                    note1 = genome.getMelody().get(i).get(idx);
                } while (note1 == 0 || note1 == -1);
            }
            int transpose = Util.getRandomNumber(1, 4);
            if (note1 >= median){
                genome.getMelody().get(i).set(idx, note1 - transpose);
            } else {
                genome.getMelody().get(i).set(idx, note1 + transpose);
            }
        }
        return genome;
    }

    public static Genome musicalContextMutation(Genome genome, ImmutableList<Integer> representation,
                                                ArrayList<HashMap<String, List<Integer>>> chrProgPattern,
                                                List<String> chrProg,
                                                int melodyKeyVal) {
        Random randomObj = new Random();

        int reprMax = representation
                .stream()
                .mapToInt(v -> v)
                .max()
                .orElseThrow();
        int reprMin = representation
                .stream()
                .mapToInt(v -> v)
                .min()
                .orElseThrow();

        List<List<Integer>> melodyArray = new ArrayList<>();
        for (List<Integer> sublist : genome.getMelody()) {
            List<Integer> copiedSublist = new ArrayList<>(sublist);
            melodyArray.add(copiedSublist);
        }

        int idx, idxLocal, closestNumber, oldNote, maxNumberOfNotes, idxCounter, chordNote, modulo;
        int neighbourRight1 = 0;
        int neighbourRight2 = 0;
        int neighbourLeft1 = 0;
        int neighbourLeft2 = 0;
        int newNote = 0;
        boolean nL1 = false, nL2 = false, nR1 = false, nR2 = false;

        for (int i = 0; i< melodyArray.size(); i++){
            List<Integer> chordNotes = chrProgPattern.get(0).get(chrProg.get(i));
            var barNotes = melodyArray.get(i);
            maxNumberOfNotes = melodyArray.get(i).size();

            idxLocal = Util.getRandomNumber(0, maxNumberOfNotes);
            oldNote = melodyArray.get(i).get(idxLocal);
            if (oldNote == 0){
                oldNote = representation.get(Util.getRandomNumber(0, representation.size() - 1));
            }

            for (int j=idxLocal-1; j>=0; j--){
                if (!nL1 || !nL2){
                    if (barNotes.get(j)!=0 && barNotes.get(j)!=-1){
                        if (!nL1){
                            neighbourLeft1 = barNotes.get(j);
                            nL1 = true;
                            continue;
                        }
                        neighbourLeft2 = barNotes.get(j);
                        nL2 = true;
                        break;
                    }
                } else{
                    break;
                }
            }
            for (int j=idxLocal+1; j<melodyArray.size(); j++){
                if (!nR1 || !nR2){
                    if (barNotes.get(j)!=0 && barNotes.get(j)!=-1){
                        if (!nR1){
                            neighbourRight1 = barNotes.get(j);
                            nR1 = true;
                            continue;
                        }
                        neighbourRight2 = barNotes.get(j);
                        nR2 = true;
                        break;
                    }
                } else{
                    break;
                }
            }

            if (!chordNotes.contains(Math.abs(neighbourRight1 - melodyKeyVal) % 12) && nR1 && nR2){
                if (!chordNotes.contains(Math.abs(neighbourRight2 - melodyKeyVal) % 12)){

                    chordNote = chordNotes.get(new Random().nextInt(chordNotes.size()));
                    modulo = Math.abs(oldNote - melodyKeyVal) % 12;

                    if (randomObj.nextDouble()<0.5){
                        newNote = oldNote + (chordNote - modulo);
                    } else {
                        newNote = oldNote - (12 - (chordNote - modulo));
                    }


                }
            } else if (!chordNotes.contains(Math.abs(neighbourLeft2 - melodyKeyVal) % 12) && nL1 && nL2) {
                if (!chordNotes.contains(Math.abs(neighbourLeft1 - melodyKeyVal) % 12)){

                    final int nonChordNote = Math.abs(neighbourLeft1 - melodyKeyVal) % 12;
                    closestNumber = chordNotes.stream()
                            .min(Comparator.comparingInt(a -> Math.abs(a - nonChordNote)))
                            .orElseThrow();
                    newNote = neighbourLeft1 + (closestNumber-nonChordNote);
                }
            } else if (!chordNotes.contains(Math.abs(neighbourLeft1 - melodyKeyVal) % 12) && nL1 && nR1) {
                if (!chordNotes.contains(Math.abs(neighbourRight1 - melodyKeyVal) % 12)) {

                    if (randomObj.nextDouble() < 0.5){

                        final int nonChordNote = Math.abs(neighbourLeft1 - melodyKeyVal) % 12;
                        closestNumber = chordNotes.stream()
                                .min(Comparator.comparingInt(a -> Math.abs(a - nonChordNote)))
                                .orElseThrow();
                        newNote = neighbourLeft1 + (closestNumber-nonChordNote);

                    } else {

                        final int nonChordNote = Math.abs(neighbourRight1 - melodyKeyVal) % 12;
                        closestNumber = chordNotes.stream()
                                .min(Comparator.comparingInt(a -> Math.abs(a - nonChordNote)))
                                .orElseThrow();
                        newNote = neighbourRight1 + (closestNumber-nonChordNote);
                    }
                }
            }
            else {
                List<Integer> generatedNumbers = new ArrayList<>();
                for (int k = reprMin; k <= reprMax; k++) {
                    if (Math.abs(k - neighbourLeft1) <= 14 && Math.abs(k - neighbourRight1) <= 14) {
                        if (Math.abs(k-oldNote) > 14){
                            generatedNumbers.add(k);
                        }
                    }
                }
                newNote = generatedNumbers.isEmpty() ?
                        representation.get(Util.getRandomNumber(0, representation.size() - 1))
                        : generatedNumbers.get(new Random().nextInt(generatedNumbers.size()));
            }

            idxCounter = -1;
            idx = 0;
            for (int j=0; j<genome.getMelody().get(i).size(); j++){
                if (genome.getMelody().get(i).get(j) != 0 && genome.getMelody().get(i).get(j) != -1){
                    idxCounter += 1;
                }
                if (idxCounter == idxLocal){
                    idx = j;
                    break;
                }
                if (j==genome.getMelody().get(i).size()-1){
                    idx = j;
                }
            }
            genome.getMelody().get(i).set(idx, newNote);
        }

        return genome;
    }


    public static Genome simpleMutation(Genome genome, ImmutableList<Integer> representation) {
        for (int i = 0; i < genome.getMelody().size(); i++) {
                int idx = Util.getRandomNumber(0, genome.getMelody().get(i).size() - 1);
                genome.getMelody().get(i).set(idx, representation.get(Util.getRandomNumber(0, representation.size() - 1)));
        }
        return genome;
    }

    public static Genome barOrderMutation(Genome genome){

        int idx1, idx2;
            idx1 = Util.getRandomNumber(0, genome.getMelody().size()-1);
            do {
                idx2 = Util.getRandomNumber(0, genome.getMelody().size()-1);
            } while (idx1 == idx2);

            Collections.swap(genome.getMelody(), idx1, idx2);

        return genome;
    }

}
