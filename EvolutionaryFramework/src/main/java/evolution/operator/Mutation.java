package evolution.operator;

import com.google.common.collect.ImmutableList;
import evolution.music.Genome;
import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

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

    public static Genome musicalContextMutation(Genome genome, ImmutableList<Integer> representation,
                                                         ArrayList<HashMap<String, List<Integer>>> chrProgPattern,
                                                         List<String> chrProg,
                                                         int melodyKeyVal){
        Random randomObj = new Random();

        int idx, closestNumber,
            neighbourRight1, neighbourRight2,
            neighbourLeft1, neighbourLeft2,
            oldNote;
        int newNote = 0;
        int maxNumberOfNotes = genome.getMelody().get(0).size();
//        boolean nL1 = false, nL2 = false, nR1 = false, nR2 = false;

        for (int i = 0; i < genome.getMelody().size(); i++) {
            List<Integer> chordNotes = chrProgPattern.get(0).get(chrProg.get(i));
            idx = Util.getRandomNumber(0, maxNumberOfNotes);
            oldNote = genome.getMelody().get(i).get(idx);
            if (oldNote != 0 && oldNote != -1) {

                neighbourRight1 = genome.getMelody().get(i).get(idx+1);
                neighbourRight2 = genome.getMelody().get(i).get(idx+2);
                neighbourLeft1 = genome.getMelody().get(i).get(idx-1);
                neighbourLeft2 = genome.getMelody().get(i).get(idx-2);

//                if (idx < maxNumberOfNotes - 2){
//                    nR2 = true;
//                    nR1 = true;
//                    neighbourRight1 = genome.getMelody().get(i).get(idx+1);
//                    neighbourRight2 = genome.getMelody().get(i).get(idx+2);
//                } else if (idx < maxNumberOfNotes - 1){
//                    nR1 = true;
//                    neighbourRight1 = genome.getMelody().get(i).get(idx+1);
//                } else if (idx > 1){
//                    nL1 = true;
//                    nL2 = true;
//                    neighbourLeft1 = genome.getMelody().get(i).get(idx-1);
//                    neighbourLeft2 = genome.getMelody().get(i).get(idx-2);
//                }
//                else if (idx > 0){
//                    nL1 = true;
//                    neighbourLeft1 = genome.getMelody().get(i).get(idx-1);
//                }

                if (!chordNotes.contains(Math.abs(neighbourRight1 - melodyKeyVal) % 12)){
                    if (!chordNotes.contains(Math.abs(neighbourRight2 - melodyKeyVal) % 12)){

                        newNote = chordNotes.get(new Random().nextInt(chordNotes.size()));
                    }
                } else if (!chordNotes.contains(Math.abs(neighbourLeft2 - melodyKeyVal) % 12)) {
                    if (!chordNotes.contains(Math.abs(neighbourLeft1 - melodyKeyVal) % 12)){

                        final int nonChordNote = Math.abs(neighbourLeft1 - melodyKeyVal) % 12;
                        closestNumber = chordNotes.stream()
                                .min(Comparator.comparingInt(a -> Math.abs(a - nonChordNote)))
                                .orElseThrow();
                        newNote = chordNotes.get(neighbourLeft1 + (closestNumber-nonChordNote));
                    }
                } else if (!chordNotes.contains(Math.abs(neighbourLeft1 - melodyKeyVal) % 12)) {
                    if (!chordNotes.contains(Math.abs(neighbourRight1 - melodyKeyVal) % 12)) {

                        if (randomObj.nextDouble() < 0.5){

                            final int nonChordNote = Math.abs(neighbourLeft1 - melodyKeyVal) % 12;
                            closestNumber = chordNotes.stream()
                                    .min(Comparator.comparingInt(a -> Math.abs(a - nonChordNote)))
                                    .orElseThrow();
                            newNote = chordNotes.get(neighbourLeft1 + (closestNumber-nonChordNote));
                        } else {

                            final int nonChordNote = Math.abs(neighbourRight1 - melodyKeyVal) % 12;
                            closestNumber = chordNotes.stream()
                                    .min(Comparator.comparingInt(a -> Math.abs(a - nonChordNote)))
                                    .orElseThrow();
                            newNote = chordNotes.get(neighbourRight1 + (closestNumber-nonChordNote));
                        }
                    }
                } else {
                    newNote = 0;
//                    double randomProbability = randomObj.nextDouble();
//                    if (randomProbability < (double)1/3){
//
//                    } else if (randomProbability < (double)2/3){
//
//                    } else {
//
//                    }
                    genome.getMelody().get(i).set(idx, representation.get(Util.getRandomNumber(0, representation.size() - 1)));
                }
                genome.getMelody().get(i).set(idx, newNote);
            } else {
                genome.getMelody().get(i).set(idx, representation.get(Util.getRandomNumber(0, representation.size() - 1)));
            }

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
