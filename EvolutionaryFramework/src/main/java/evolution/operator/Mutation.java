package evolution.operator;

import com.google.common.collect.ImmutableList;
import evolution.music.Genome;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Mutation {
    public static Genome mutation(double mutationProbability, List<Pair<String, Double>> mutationType, Genome genome, ImmutableList<Integer> representation, int generationNumber) {
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
            case "BAR_ORDER_AND_SIMPLE":
                return barOrderAndSimpleMutation(genome, representation);
            case "NO_MUTATION":
            default:
                return genome;
        }
    }

    public static Genome simpleMutation(Genome genome, ImmutableList<Integer> representation) {
        Random randomObj = new Random();
        double randomNumber;
        int mutation;
        for (int i = 0; i < genome.getMelody().size(); i++) {
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

    public static Genome barOrderAndSimpleMutation(Genome genome, ImmutableList<Integer> representation){
        Genome simpleMutationGenome = simpleMutation(genome, representation);
        return barOrderMutation(simpleMutationGenome);
    }

}
