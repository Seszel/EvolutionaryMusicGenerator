package evolution.population;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import evolution.music.Melody;
import evolution.music.Representation;
import evolution.operator.crossover.OnePointCrossover;
import evolution.operator.matingPoolSelection.MOEA_D_MatingPoolSelection;
import evolution.operator.mutatation.SimpleMutation;
import evolution.solution.Individual;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class PopulationMOEA_D extends Population {

    private ArrayList<Individual> externalPopulation;
    private ArrayList<ArrayList<Pair<Integer, Double>>> euclideanDistancesWeightVectors;
    private ArrayList<Pair<Integer, ArrayList<Double>>> weightVectors;
    private ArrayList<List<Integer>> neighbours;
    private ArrayList<Double> referencePointsZ;

    public PopulationMOEA_D(int popSize, String representationType, List<String> criteria, int numberOfBars, int maxNumberOfNotes, List<String> chordProgression, String melodyKey) {
        super(popSize, representationType, criteria, numberOfBars, maxNumberOfNotes, chordProgression, melodyKey);
    }

    public void setEuclideanDistancesWeightVectors() {
        ArrayList<ArrayList<Pair<Integer, Double>>> euclideanDistancesWeightVectors = new ArrayList<>();
        for (Pair<Integer, ArrayList<Double>> weightVector1 : weightVectors) {
            ArrayList<Pair<Integer, Double>> temp = new ArrayList<>();
            for (int i = 0; i < weightVectors.size(); i++) {
                temp.add(new ImmutablePair<>(i, Util.getEuclideanDistance(weightVector1.getValue(), weightVectors.get(i).getValue())));
            }
            euclideanDistancesWeightVectors.add(temp);
        }
        this.euclideanDistancesWeightVectors = euclideanDistancesWeightVectors;
    }

    public ArrayList<ArrayList<Pair<Integer, Double>>> getEuclideanDistancesWeightVectors() {
        return euclideanDistancesWeightVectors;
    }

    public void setNeighbours(int numberOfNeighbours) {
        ArrayList<List<Integer>> neighbours = new ArrayList<>();
        for (ArrayList<Pair<Integer, Double>> distances : euclideanDistancesWeightVectors) {
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
        ArrayList<Pair<Integer, ArrayList<Double>>> weightVectors = new ArrayList<>();
        Random rand = new Random();
        double random;
        double normalization;
        for (int i = 0; i < popSize; i++) {
            ArrayList<Double> temp = new ArrayList<>();
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

    public ArrayList<Pair<Integer, ArrayList<Double>>> getWeightVectors() {
        return weightVectors;
    }

    public ArrayList<List<Integer>> getNeighbours() {
        return neighbours;
    }

    public void updateNeighboursSolutions(int idxIndividual, Individual newIndividual) {
        double newSolutionDifference = 0;
        ArrayList<Double> newSolutionFitness = newIndividual.getFitness();
        for (int c = 0; c < criteria.size(); c++) {
            newSolutionDifference += Math.abs(newSolutionFitness.get(c) - referencePointsZ.get(c));
        }

        ArrayList<Double> neighbourSolutionFitness;
        double neighbourSolutionDifference;
        for (int i = 0; i < neighbours.get(idxIndividual).size(); i++) {
            neighbourSolutionDifference = 0;
            neighbourSolutionFitness = population.get(neighbours.get(idxIndividual).get(i)).getFitness();
            for (int c = 0; c < criteria.size(); c++) {
                neighbourSolutionDifference += Math.abs(neighbourSolutionFitness.get(c) - referencePointsZ.get(c));
            }
            if (neighbourSolutionDifference >= newSolutionDifference) {
                population.set(neighbours.get(idxIndividual).get(i), newIndividual);
            }
        }
    }

    public void update(ImmutableList<Integer> representation) {
        ArrayList<HashMap<String, List<Integer>>> chordProgressionPattern = Representation.getChordProgressionMajor();
        BiMap<String, Integer> notesMap = Representation.getNotesMap();
        int melodyKeyValue = notesMap.get(melodyKey);

        ArrayList<Pair<Individual, Individual>> matingPool = MOEA_D_MatingPoolSelection.matingPoolSelection(popSize, popSize, this);
        SplittableRandom random = new SplittableRandom();
        Individual offspring;

        for (int p = 0; p < matingPool.size(); p++) {
            Pair<Melody, Melody> offsprings = OnePointCrossover.crossover(matingPool.get(p), numberOfBars, maxNumberOfNotes);
            if (random.nextInt(1, 101) <= 50) {
                offspring = new Individual(SimpleMutation.mutation(offsprings.getLeft(), representation, numberOfBars, maxNumberOfNotes));
            } else {
                offspring = new Individual(SimpleMutation.mutation(offsprings.getRight(), representation, numberOfBars, maxNumberOfNotes));
            }
            offspring.getGenome().setMelodyJFugue(maxNumberOfNotes);
            offspring.setFitness(criteria, chordProgressionPattern, chordProgression, melodyKeyValue);
            updateNeighboursSolutions(p, offspring);
            updateExternalPopulation(offspring);
        }
    }

    public ArrayList<Double> getReferencePointsZ() {
        return referencePointsZ;
    }

    public void setExternalPopulation(){
        this.externalPopulation = new ArrayList<>();
    }
    public void updateExternalPopulation(Individual offspring) {
        ArrayList<Individual> newExternalPopulation = new ArrayList<>(externalPopulation);
        boolean dominates = true;
        for (Individual individual : externalPopulation) {
            if (offspring.dominates(individual)) {
                newExternalPopulation.remove(individual);
            } else {
                dominates = false;
            }
        }
        if (dominates) {
            newExternalPopulation.add(offspring);
        }
        this.externalPopulation = newExternalPopulation;
    }

    public void setReferencePointsZ() {
        ArrayList<Double> bestSoFar = new ArrayList<>();
        for (Individual individual : population) {
            if (bestSoFar.size() == 0) {
                for (int c = 0; c < criteria.size(); c++) {
                    bestSoFar.add(individual.getFitness().get(c));
                }
            }
            for (int c = 0; c < criteria.size(); c++) {
                if (individual.getFitness().get(c) > bestSoFar.get(c)) {
                    bestSoFar.set(c, individual.getFitness().get(c));
                }
            }
        }
        this.referencePointsZ = bestSoFar;
    }
}
