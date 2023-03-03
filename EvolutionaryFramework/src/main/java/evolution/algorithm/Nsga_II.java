package evolution.algorithm;

public class Nsga_II extends AEvolutionaryAlgorithm{

    public Nsga_II(int crossoverType, int mutationType, int selectionType, int matingPoolSelectionType) {
        super(crossoverType, mutationType, selectionType, matingPoolSelectionType);
    }

    @Override
    public void algorithm() {
        System.out.println("Nsga_II algorithm!");
    }


}
