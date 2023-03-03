package evolution.operator.mutatation;

public interface IMutation {
    default void mutation(){
        System.out.println("Mutation operator");
    }
}
