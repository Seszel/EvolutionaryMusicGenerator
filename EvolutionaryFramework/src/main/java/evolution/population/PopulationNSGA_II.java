package evolution.population;

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

        ArrayList<ArrayList<Pair<Integer, Individual>>> solutionsDominated = new ArrayList<>();
        ArrayList<Integer> dominationCount = new ArrayList<>();
        ArrayList<ArrayList<Pair<Integer, Individual>>> fronts = new ArrayList<>();

        fronts.add(new ArrayList<>());
        for (int i = 0; i < popSize; i++) {
            solutionsDominated.add(new ArrayList<>());
            dominationCount.add(0);
            for (int j = 0; j < popSize; j++) {
                if (population.get(i).dominates(population.get(j))) {
                    solutionsDominated.get(i).add(new Pair<>(j,population.get(j)));
                } else if (population.get(j).dominates(population.get(i))) {
                    dominationCount.set(i, dominationCount.get(i) + 1);
                }
            }
            if (dominationCount.get(i) == 0) {
                population.get(i).setFrontRank(1);
                fronts.get(0).add(new Pair<>(i, population.get(i)));
            }
        }
        int i = 0;
        ArrayList<Pair<Integer, Individual>> front = fronts.get(0);
        while (front.size() != 0) {
            ArrayList<Pair<Integer, Individual>> Q = new ArrayList<>();
            for (int j = 0; j < fronts.get(i).size(); j++) {
                for (int k = 0; k < solutionsDominated.get(fronts.get(i).get(j).fst).size(); k++){
                    dominationCount.set(solutionsDominated.get(fronts.get(i).get(j).fst).get(k).fst, dominationCount.get(solutionsDominated.get(fronts.get(i).get(j).fst).get(k).fst)-1);
                    if (dominationCount.get(solutionsDominated.get(fronts.get(i).get(j).fst).get(k).fst) == 0){
                        solutionsDominated.get(fronts.get(i).get(j).fst).get(k).snd.setFrontRank(i+1+1);
                        Q.add(new Pair<>(solutionsDominated.get(fronts.get(i).get(j).fst).get(k).fst, solutionsDominated.get(fronts.get(i).get(j).fst).get(k).snd));
                    }
                }
            }
            i += 1;
            front = Q;
            if (Q.size() != 0){ fronts.add(Q); }
        }
    }


}
