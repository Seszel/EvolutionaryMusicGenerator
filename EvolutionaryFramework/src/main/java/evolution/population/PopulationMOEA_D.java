package evolution.population;

import evolution.objective.EvaluationParameters;
import evolution.solution.Individual;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class PopulationMOEA_D extends Population {

    private List<Individual> externalPopulation;
    private List<List<Pair<Integer, Double>>> euclideanDistancesWeightVectors;
    private List<Pair<Integer, List<Double>>> weightVectors;
    private List<List<Integer>> neighbours;
    private HashMap<String, Double> referencePointsZ;

    public PopulationMOEA_D(int popSize, String representationType, List<String> criteria,
                            int numberOfBars, int maxNumberOfNotes,
                            List<String> chordProgression, Pair<String, String> melodyKey,
                            EvaluationParameters evalParams) {
        super(popSize, representationType, criteria, numberOfBars,
                maxNumberOfNotes, chordProgression, melodyKey, evalParams);
    }

    public void setEuclideanDistancesWeightVectors() {
        List<List<Pair<Integer, Double>>> euclideanDistancesWeightVectors = new ArrayList<>();
        for (Pair<Integer, List<Double>> weightVector1 : weightVectors) {
            List<Pair<Integer, Double>> temp = new ArrayList<>();
            for (int i = 0; i < weightVectors.size(); i++) {
                temp.add(new ImmutablePair<>(i, Util.getEuclideanDistance(weightVector1.getValue(), weightVectors.get(i).getValue())));
            }
            euclideanDistancesWeightVectors.add(temp);
        }
        this.euclideanDistancesWeightVectors = euclideanDistancesWeightVectors;
    }

    public List<List<Pair<Integer, Double>>> getEuclideanDistancesWeightVectors() {
        return euclideanDistancesWeightVectors;
    }

    public void setNeighbours(int numberOfNeighbours) {
        List<List<Integer>> neighbours = new ArrayList<>();
        for (List<Pair<Integer, Double>> distances : euclideanDistancesWeightVectors) {
            distances.sort(Comparator.comparing(Pair::getRight));
            neighbours.add(distances
                    .subList(0, (numberOfNeighbours))
                    .stream()
                    .map(Pair::getKey)
                    .collect(Collectors.toList()));
        }
        this.neighbours = neighbours;
    }

    public void setWeightVectors() {
        List<Pair<Integer, List<Double>>> weightVectors = new ArrayList<>();
        Random rand = new Random();
        double random;
        double normalization;
        for (int i = 0; i < popSize; i++) {
            List<Double> temp = new ArrayList<>();
            normalization = 0;
            for (int j = 0; j < criteria.size(); j++) {
                random = rand.nextDouble();
                normalization += random;
                temp.add(random);
            }
            for (int j = 0; j < temp.size(); j++) {
                temp.set(j, temp.get(j) / normalization);
            }
            weightVectors.add(new ImmutablePair<>(i, temp));
        }
        this.weightVectors = weightVectors;
    }

    public List<Pair<Integer, List<Double>>> getWeightVectors() {
        return weightVectors;
    }

    public List<List<Integer>> getNeighbours() {
        return neighbours;
    }

    public void updateNeighboursSolutions(int idxIndividual, Individual newIndividual) {
        HashMap<String, Double> newSolutionFitness = newIndividual.getFitness();
        HashMap<String, Double> tempDifference = new HashMap<>();
        HashMap<String, Double> newSolutionDifference = new HashMap<>();
        String maxTempDifferenceKey;
        String maxNewSolutionDifferenceKey;
        double lambdas;
        for (int neighbour : neighbours.get(idxIndividual)) {
            for (int c = 0; c < criteria.size(); c++) {
                lambdas = weightVectors.get(neighbour).getValue().get(c);

                tempDifference.put(criteria.get(c), lambdas * Math.abs(population.get(neighbour).getFitnessByName(criteria.get(c)) - referencePointsZ.get(criteria.get(c))));
                newSolutionDifference.put(criteria.get(c), lambdas * Math.abs(newSolutionFitness.get(criteria.get(c)) - referencePointsZ.get(criteria.get(c))));
            }

            maxTempDifferenceKey = Collections.max(tempDifference.keySet());
            maxNewSolutionDifferenceKey = Collections.max(newSolutionDifference.keySet());

            if (newSolutionDifference.get(maxNewSolutionDifferenceKey) <= tempDifference.get(maxTempDifferenceKey))
                population.set(neighbour, newIndividual);
        }
    }

    public HashMap<String, Double> getReferencePointsZ() {
        return referencePointsZ;
    }

    public List<Individual> getExternalPopulation() {
        return externalPopulation;
    }

    public void setExternalPopulation() {
        this.externalPopulation = new ArrayList<>();
    }

    public void updateExternalPopulation(Individual offspring) {

        boolean dominates = true;
        Iterator<Individual> itr = externalPopulation.iterator();
        while (itr.hasNext()) {
            Individual individual = itr.next();
            if (offspring.dominates(individual)) {
                itr.remove();
            } else {
                dominates = false;
            }
        }
        if (dominates){
            externalPopulation.add(offspring);
        }
    }

    public void setReferencePointsZ() {
        HashMap<String, Double> bestSoFar = new HashMap<>();
        for (Individual individual : population) {
            if (bestSoFar.size() == 0) {
                for (String criterion : criteria) {
                    bestSoFar.put(criterion, individual.getFitnessByName(criterion));
                }
            }
            for (String criterion : criteria) {
                if (individual.getFitnessByName(criterion) > bestSoFar.get(criterion)) {
                    bestSoFar.put(criterion, individual.getFitnessByName(criterion));
                }
            }
        }
        this.referencePointsZ = bestSoFar;
    }
    public void updateReferencePointsZ(Individual newIndividual){
        for (String criterion : criteria) {
            if (newIndividual.getFitnessByName(criterion) > referencePointsZ.get(criterion)) {
                referencePointsZ.put(criterion, newIndividual.getFitnessByName(criterion));
            }
        }
    }
}
