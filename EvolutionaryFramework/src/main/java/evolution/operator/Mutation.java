package evolution.operator;

import com.google.common.collect.ImmutableList;
import evolution.music.Melody;
import evolution.util.Util;

import java.util.SplittableRandom;

public class Mutation {
    public static Melody simpleMutation(Melody melody, ImmutableList<Integer> representation, int numberOfBars, int maxNumberOfNotes) {
        SplittableRandom random = new SplittableRandom();
        for (int i = 0; i < numberOfBars; i++) {
//            random.nextInt(1, 101);
            int idx = Util.getRandomNumber(0, maxNumberOfNotes - 1);
            int mutation = representation.get(Util.getRandomNumber(0, representation.size()-1));
            melody.getMelody().get(i).set(idx, mutation);
        }
        return melody;
    }
}
