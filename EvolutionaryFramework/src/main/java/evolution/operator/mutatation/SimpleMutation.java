package evolution.operator.mutatation;

import com.google.common.collect.ImmutableList;
import evolution.helper.Helper;
import evolution.music.Melody;

import java.util.SplittableRandom;

public class SimpleMutation implements IMutation {
    public static Melody mutation(Melody melody, ImmutableList<Integer> representation) {
        SplittableRandom random = new SplittableRandom();
        for (int i = 0; i < melody.getNumberOfBars(); i++) {
            if (random.nextInt(1, 1001) <= 100) {
                int idx = Helper.getRandomNumber(0, melody.getMaxNumberOfNotes() - 1);
                int mutation = representation.get(Helper.getRandomNumber(0, representation.size()));
                melody.getMelody().get(i).set(idx, mutation);
            }
        }
        return melody;
    }
}
