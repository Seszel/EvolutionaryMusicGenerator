package evolution.operator.selection;

public interface ISelection {
    default void selection(){
        System.out.println("Selection operator");
    }
}
