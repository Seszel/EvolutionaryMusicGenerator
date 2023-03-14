package evolution.population;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Pair;
import evolution.solution.Individual;

import java.util.ArrayList;

public class PopulationNSGA_II extends Population {

    public PopulationNSGA_II(int popSize, String representationType, int numberOfBars, int maxNumberOfNotes, List<String> chordProgression, String melodyKey) {
        super(popSize, representationType, numberOfBars, maxNumberOfNotes, chordProgression, melodyKey);
    }

    @Override
    public void sortPopulation() {
        ArrayList<Individual> sortedPopulation = new ArrayList<>();

        BiMap<Integer, Individual> integerIndividualBiMap = HashBiMap.create();

        ArrayList<ArrayList<Individual>> solutionsDominated = new ArrayList<>();
        ArrayList<Integer> dominationCount = new ArrayList<>();
        ArrayList<ArrayList<Individual>> fronts = new ArrayList<>();

        fronts.add(new ArrayList<>());
        for (int i = 0; i < popSize; i++) {
            integerIndividualBiMap.put(i, population.get(i));
            solutionsDominated.add(new ArrayList<>());
            dominationCount.add(0);
            for (int j = 0; j < popSize; j++) {
                if (population.get(i).dominates(population.get(j))) {
                    solutionsDominated.get(i).add(population.get(j));
                } else if (population.get(j).dominates(population.get(i))) {
                    dominationCount.set(i, dominationCount.get(i) + 1);
                }
            }
            if (dominationCount.get(i) == 0) {
                population.get(i).setFrontRank(1);
                fronts.get(0).add(population.get(i));
            }
        }
        int i = 0;
        ArrayList<Individual> front = fronts.get(0);
        Individual p;
        Integer pId;
        Individual q;
        Integer qId;
        while (front.size() != 0) {
            ArrayList<Individual> Q = new ArrayList<>();
            for (int j = 0; j < fronts.get(i).size(); j++) {
                p = fronts.get(i).get(j);
                pId = integerIndividualBiMap.inverse().get(p);
                for (int k = 0; k < solutionsDominated.get(pId).size(); k++){
                    q = solutionsDominated.get(pId).get(k);
                    qId = integerIndividualBiMap.inverse().get(q);
                    dominationCount.set(qId, dominationCount.get(qId)-1);
                    if (dominationCount.get(qId) == 0){
                        q.setFrontRank(i+1+1);
                        Q.add(q);
                    }
                }
            }
            i += 1;
            front = Q;
            if (Q.size() != 0){ fronts.add(Q); }
        }
    }


}
