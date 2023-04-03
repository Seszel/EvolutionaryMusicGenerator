package evolution.operator;

import com.google.common.collect.ImmutableList;
import evolution.music.Genome;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Random;

public class Mutation {
    public static Genome mutation(Pair<String, Double> mutationType, Genome genome, ImmutableList<Integer> representation) {
        switch (mutationType.getLeft()) {
            case "SIMPLE":
                return simpleMutation(genome, representation, mutationType.getRight());
            default:
                return null;
        }
    }

    public static Genome simpleMutation(Genome genome, ImmutableList<Integer> representation, double probability) {
        Random randomObj = new Random();
        for (int i = 0; i < genome.getMelody().size(); i++) {
            if (randomObj.nextDouble() <= probability) {
                int idx = Util.getRandomNumber(0, genome.getMelody().get(i).size() - 1);
                int mutation = representation.get(Util.getRandomNumber(0, representation.size() - 1));
                genome.getMelody().get(i).set(idx, mutation);
            }
        }
        return genome;
    }

}
