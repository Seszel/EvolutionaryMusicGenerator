package evolution.operator.crossover;

public interface ICrossover {
    default void crossover(){
        System.out.println("ICrossover operator");
    }
}
