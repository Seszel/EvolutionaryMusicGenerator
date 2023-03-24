package evolution.population;

import com.google.common.collect.ImmutableList;
import evolution.operator.matingPoolSelection.MOEA_D_MatingPoolSelection;
import evolution.operator.matingPoolSelection.TournamentMatingPoolSelection;
import evolution.solution.Individual;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PopulationMOEA_D extends Population {

    private ArrayList<Individual> externalPopulation;
    private ArrayList<ArrayList<Pair<Integer, Double>>> euclideanDistancesWeightVectors;
    private ArrayList<Pair<Integer,ArrayList<Double>>> weightVectors;
    private ArrayList<List<Integer>> neighbours;

    public PopulationMOEA_D(int popSize, String representationType, List<String> criteria, int numberOfBars, int maxNumberOfNotes, List<String> chordProgression, String melodyKey) {
        super(popSize, representationType, criteria, numberOfBars, maxNumberOfNotes, chordProgression, melodyKey);
    }

    public void setEuclideanDistancesWeightVectors() {
        ArrayList<ArrayList<Pair<Integer, Double>>> euclideanDistancesWeightVectors = new ArrayList<>();
        for (Pair<Integer,ArrayList<Double>> weightVector1 : weightVectors) {
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
                    .subList(1, (numberOfNeighbours + 1))
                    .stream()
                    .map(Pair::getKey)
                    .collect(Collectors.toList()));
        }
        this.neighbours = neighbours;
    }

    public void setWeightVectors() {
        ArrayList<Pair<Integer,ArrayList<Double>>> weightVectors = new ArrayList<>();
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

    public ArrayList<Pair<Integer,ArrayList<Double>>> getWeightVectors() {
        return weightVectors;
    }

    public ArrayList<List<Integer>> getNeighbours() {
        return neighbours;
    }

    public void reproduction(ImmutableList<Integer> representation){
        ArrayList<Pair<Individual, Individual>> matingPool = MOEA_D_MatingPoolSelection.matingPoolSelection(popSize, popSize, this);
    }

}
