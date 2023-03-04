package evolution.algorithm;

import evolution.music.RepresentationMap;

import java.util.Map;

public class Nsga_II extends AEvolutionaryAlgorithm {

    public Nsga_II(int popSize, String representationType, String crossoverType, String mutationType, String selectionType, String matingPoolSelectionType) {
        super(popSize, representationType, crossoverType, mutationType, selectionType, matingPoolSelectionType);
    }

    public void setRepresentationMap() {

        RepresentationMap representation = new RepresentationMap();
        this.representationMap = representation.getRepresentation(this.representationType);

    }

    @Override
    public void algorithm() {
        System.out.println("Nsga_II algorithm!");
    }


}
