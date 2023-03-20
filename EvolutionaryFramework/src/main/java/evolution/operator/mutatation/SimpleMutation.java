package evolution.operator.mutatation;

import com.google.common.collect.ImmutableList;
import evolution.util.Util;
import evolution.music.Melody;

import java.util.SplittableRandom;

public class SimpleMutation implements IMutation {
    public static Melody mutation(Melody melody, ImmutableList<Integer> representation, int numberOfBars, int maxNumberOfNotes) {
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
