package evolution.operator.crossover;

import com.sun.tools.javac.util.Pair;
import evolution.music.Melody;
import evolution.solution.Individual;

public interface ICrossover {
    static Pair<Melody,Melody> crossover(){
        System.out.println("ICrossover operator");
        return null;
    }
}
