package evolution.operator.mutatation;

import com.google.common.collect.ImmutableList;
import evolution.music.Melody;

public interface IMutation {
    static Melody mutation(Melody melody, ImmutableList<Integer> representation){
        System.out.println("Mutation operator");
        return null;
    }
}
