package evolution.operator.crossover;

import evolution.music.Melody;
import org.apache.commons.lang3.tuple.Pair;

public interface ICrossover {
    static Pair<Melody,Melody> crossover(){
        System.out.println("ICrossover operator");
        return null;
    }
}
