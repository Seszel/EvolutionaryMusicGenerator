package evolution.operator.matingPoolSelection;

public interface IMatingPoolSelection {
    default void matingPoolSelection(){
        System.out.println("MatingPoolSelection operator");
    }
}
