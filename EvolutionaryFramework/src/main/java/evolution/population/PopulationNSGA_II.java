package evolution.population;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import evolution.music.Melody;
import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.operator.crossover.OnePointCrossover;
import evolution.operator.mutatation.SimpleMutation;
import evolution.solution.Individual;
import lombok.var;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class PopulationNSGA_II extends Population {

    private ArrayList<ArrayList<Individual>> fronts;
    private ArrayList<Individual> offsprings;

    public PopulationNSGA_II(int popSize, String representationType, List<String> criteria,
                             int numberOfBars, int maxNumberOfNotes,
                             List<String> chordProgression, String melodyKey,
                             EvaluationParameters evalParams) {
        super(popSize, representationType, criteria, numberOfBars,
                maxNumberOfNotes, chordProgression, melodyKey, evalParams);
    }

    @Override
    public void generateFronts() {
        BiMap<Integer, Individual> integerIndividualBiMap = HashBiMap.create();

        ArrayList<ArrayList<Individual>> solutionsDominated = new ArrayList<>();
        ArrayList<Integer> dominationCount = new ArrayList<>();
        ArrayList<ArrayList<Individual>> fronts = new ArrayList<>();

        fronts.add(new ArrayList<>());
        for (int i = 0; i < population.size(); i++) {
            integerIndividualBiMap.put(i, population.get(i));
            solutionsDominated.add(new ArrayList<>());
            dominationCount.add(0);
            for (Individual individual : population) {
                if (population.get(i).dominates(individual)) {
                    solutionsDominated.get(i).add(individual);
                } else if (individual.dominates(population.get(i))) {
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

    public void crowdingDistanceAssignment(ArrayList<Individual> frontSolutions) {

        double maxMin;
        double distance;
        for (String criterion : criteria) {

            frontSolutions.sort(Comparator.comparing(o -> o.getFitnessByName(criterion)));
            frontSolutions
                    .get(0)
                    .setCrowdingDistance(Double.POSITIVE_INFINITY);

            frontSolutions
                    .get(frontSolutions.size() - 1)
                    .setCrowdingDistance(Double.POSITIVE_INFINITY);

            maxMin = frontSolutions.get(frontSolutions.size() - 1)
                    .getFitnessByName(criterion) - frontSolutions.get(0).getFitnessByName(criterion);

            for (int i = 1; i < frontSolutions.size() - 1; i++) {
                distance = frontSolutions.get(i).getCrowdingDistance()
                        + (frontSolutions.get(i + 1).getFitnessByName(criterion) - frontSolutions.get(i - 1).getFitnessByName(criterion))
                        / maxMin;
                frontSolutions.get(i).setCrowdingDistance(distance);
            }
        }
    }

    public void crowdedComparisonOperator(ArrayList<ArrayList<Individual>> newPopulation) {
        for (ArrayList<Individual> front : newPopulation) {
            front.sort(Comparator.comparing(Individual::getCrowdingDistance).reversed());
        }
    }


    public void createOffsprings(ArrayList<Pair<Individual, Individual>> matingPool, ImmutableList<Integer> representation) {
        ArrayList<Individual> offsprings = new ArrayList<>();

        Pair<Melody, Melody> offspringsCrossover;
        for (Pair<Individual, Individual> individualIndividualPair : matingPool) {
            offspringsCrossover = OnePointCrossover.crossover(individualIndividualPair, numberOfBars, maxNumberOfNotes);
            offsprings.add(new Individual(SimpleMutation.mutation(offspringsCrossover.getLeft(), representation, numberOfBars, maxNumberOfNotes))
                    .addCriterion("STABILITY")
                    .addCriterion("TENSION"));
            offsprings.add(new Individual(SimpleMutation.mutation(offspringsCrossover.getRight(), representation, numberOfBars, maxNumberOfNotes))
                    .addCriterion("STABILITY")
                    .addCriterion("TENSION"));
        }

        this.offsprings = offsprings;
    }

    public void changePopulation() {
        population.addAll(offsprings);
        for (Individual individual : population) {
            individual.setFitness(this.evalParams);
        }
    }

    public ArrayList<ArrayList<Individual>> getFronts() {
        return fronts;
    }
}
