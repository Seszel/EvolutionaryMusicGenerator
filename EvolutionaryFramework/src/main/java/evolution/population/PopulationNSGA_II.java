package evolution.population;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sun.tools.javac.util.List;
import evolution.solution.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class PopulationNSGA_II extends Population {

    private ArrayList<ArrayList<Individual>> fronts;

    public PopulationNSGA_II(int popSize, String representationType, int numberOfBars, int maxNumberOfNotes, List<String> chordProgression, String melodyKey) {
        super(popSize, representationType, numberOfBars, maxNumberOfNotes, chordProgression, melodyKey);
    }

    @Override
    public void generateFronts() {
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
                for (int k = 0; k < solutionsDominated.get(pId).size(); k++) {
                    q = solutionsDominated.get(pId).get(k);
                    qId = integerIndividualBiMap.inverse().get(q);
                    dominationCount.set(qId, dominationCount.get(qId) - 1);
                    if (dominationCount.get(qId) == 0) {
                        q.setFrontRank(i + 1 + 1);
                        Q.add(q);
                    }
                }
            }
            i += 1;
            front = Q;
            if (Q.size() != 0) {
                fronts.add(Q);
            }
        }
        this.fronts = fronts;
    }

    public void crowdingDistanceAssignment() {

        for (ArrayList<Individual> frontSolutions : fronts) {

            ArrayList<Double> frontDistances = new ArrayList<>(Collections.nCopies(frontSolutions.size(), 0.0));
            frontDistances.set(0, Double.POSITIVE_INFINITY);
            frontDistances.set(frontSolutions.size() - 1, Double.POSITIVE_INFINITY);

            frontSolutions.sort(Comparator.comparing(Individual::getFitness1));
            double maxMin1 = frontSolutions.stream().max(Comparator.comparing(Individual::getFitness1)).orElseThrow(NoSuchElementException::new).getFitness1()
                    - frontSolutions.stream().min(Comparator.comparing(Individual::getFitness1)).orElseThrow(NoSuchElementException::new).getFitness1();
            for (int i = 1; i < frontSolutions.size() - 1; i++) {
                double distance1 = frontDistances.get(i)
                        + (frontSolutions.get(i + 1).getFitness1() - frontSolutions.get(i - 1).getFitness1())
                        / maxMin1;
                frontDistances.set(i, distance1);
            }
            frontSolutions.sort(Comparator.comparing(Individual::getFitness2));
            double maxMin2 = frontSolutions.stream().max(Comparator.comparing(Individual::getFitness2)).orElseThrow(NoSuchElementException::new).getFitness2()
                    - frontSolutions.stream().min(Comparator.comparing(Individual::getFitness2)).orElseThrow(NoSuchElementException::new).getFitness2();
            for (int i = 1; i < frontSolutions.size() - 1; i++) {
                double distance2 = frontDistances.get(i)
                        + (frontSolutions.get(i + 1).getFitness2() - frontSolutions.get(i - 1).getFitness2())
                        / maxMin2;
                frontDistances.set(i, distance2);
                frontSolutions.get(i).setCrowdingDistance(distance2);
            }
        }
    }

    public void crowdedComparisonOperator() {
        for (ArrayList<Individual> front : fronts) {
            front.sort(Comparator.comparing(Individual::getCrowdingDistance).reversed());
        }
    }


}
