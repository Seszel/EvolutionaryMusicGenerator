package evolution.operator;

import com.google.common.collect.ImmutableList;
import evolution.music.Genome;
import evolution.util.Util;

public class Mutation {
    public static Genome simpleMutation(Genome genome, ImmutableList<Integer> representation, int numberOfBars, int maxNumberOfNotes) {
        for (int i = 0; i < numberOfBars; i++) {
            int idx = Util.getRandomNumber(0, maxNumberOfNotes - 1);
            int mutation = representation.get(Util.getRandomNumber(0, representation.size()-1));
            genome.getMelody().get(i).set(idx, mutation);
        }
        return genome;
    }
}
