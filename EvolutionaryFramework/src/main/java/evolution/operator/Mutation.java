package evolution.operator;

import com.google.common.collect.ImmutableList;
import evolution.music.Genome;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.Random;

public class Mutation {
    public static Genome mutation(Pair<String, Double> mutationType, Genome genome, ImmutableList<Integer> representation, int generationNumber) {
        switch (mutationType.getLeft()) {
            case "SIMPLE":
                return simpleMutation(genome, representation, mutationType.getRight(), generationNumber);
            case "BAR_ORDER":
                return barOrderMutation(genome, mutationType.getRight(), generationNumber);
            case "BAR_ORDER_AND_SIMPLE":
                return barOrderAndSimpleMutation(genome, representation, mutationType.getRight(), generationNumber);
            default:
                return null;
        }
    }

    public static Genome simpleMutation(Genome genome, ImmutableList<Integer> representation, double probability, int generationNumber) {
        Random randomObj = new Random();
        double randomNumber;
        int mutation;
        for (int i = 0; i < genome.getMelody().size(); i++) {
//            if (randomObj.nextDouble() <= (probability * Math.exp(-0.02 * generationNumber) ) ) {
            if (randomObj.nextDouble() <= (probability ) ) {
                int idx = Util.getRandomNumber(0, genome.getMelody().get(i).size() - 1);
                randomNumber = randomObj.nextDouble();
                if (randomNumber <= 0.1) {
                    mutation = -1;
                } else if (randomNumber <= 0.5){
                    mutation = 0;
                } else {
                    mutation = representation.get(Util.getRandomNumber(0, representation.size() - 1));
                }

                genome.getMelody().get(i).set(idx, mutation);
            }
        }
        return genome;
    }

    public static Genome barOrderMutation(Genome genome, double probability, int generationNumber){

        Random randomObj = new Random();
        int idx1, idx2;
//        if (randomObj.nextDouble() <= probability * Math.exp(-0.02 * generationNumber)  ) {
        if (randomObj.nextDouble() <= probability  ) {
            idx1 = Util.getRandomNumber(0, genome.getMelody().size()-1);
            do {
                idx2 = Util.getRandomNumber(0, genome.getMelody().size()-1);
            } while (idx1 == idx2);

            Collections.swap(genome.getMelody(), idx1, idx2);
        }

        return genome;
    }

    public static Genome barOrderAndSimpleMutation(Genome genome, ImmutableList<Integer> representation, double probability, int generationNumber){
        Genome simpleMutationGenome = simpleMutation(genome, representation, probability, generationNumber);
        return barOrderMutation(simpleMutationGenome, probability, generationNumber);
    }

}
