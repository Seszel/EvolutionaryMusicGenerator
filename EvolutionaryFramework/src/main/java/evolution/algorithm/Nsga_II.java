package evolution.algorithm;

public class Nsga_II extends AEvolutionaryAlgorithm{

    protected Nsga_II(String popSize, String representationType, String crossoverType, String mutationType, String selectionType, String matingPoolSelectionType) {
        super(popSize, representationType, crossoverType, mutationType, selectionType, matingPoolSelectionType);
    }

    @Override
    public void algorithm() {
        System.out.println("Nsga_II algorithm!");
    }


}
