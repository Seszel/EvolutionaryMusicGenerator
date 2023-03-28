package evolution.population;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import evolution.music.Melody;
import evolution.music.Representation;
import evolution.operator.Crossover;
import evolution.operator.Mutation;
import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class PopulationNSGA_II extends Population {

    private List<List<Individual>> fronts;
    private List<Individual> offsprings;

    public PopulationNSGA_II(int popSize, String representationType, List<String> criteria, int numberOfBars, int maxNumberOfNotes, List<String> chordProgression, String melodyKey) {
        super(popSize, representationType, criteria, numberOfBars, maxNumberOfNotes, chordProgression, melodyKey);
    }

    public void generateFronts() {
        BiMap<Integer, Individual> integerIndividualBiMap = HashBiMap.create();

        List<List<Individual>> solutionsDominated = new ArrayList<>();
        List<Integer> dominationCount = new ArrayList<>();
        List<List<Individual>> fronts = new ArrayList<>();

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
        List<Individual> front = fronts.get(0);
        Individual p;
        Integer pId;
        Individual q;
        Integer qId;
        while (front.size() != 0) {
            List<Individual> Q = new ArrayList<>();
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

    public void crowdingDistanceAssignment(List<Individual> frontSolutions) {

        double maxMin;
        double distance;
        for (String criterion : criteria) {
            switch (criterion) {
                case "stability":
                    frontSolutions.sort(Comparator.comparing(Individual::getFitnessStability));
                    frontSolutions.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
                    frontSolutions.get(frontSolutions.size() - 1).setCrowdingDistance(Double.POSITIVE_INFINITY);

                    maxMin = frontSolutions.stream().max(Comparator.comparing(Individual::getFitnessStability)).orElseThrow(NoSuchElementException::new).getFitnessStability()
                            - frontSolutions.stream().min(Comparator.comparing(Individual::getFitnessStability)).orElseThrow(NoSuchElementException::new).getFitnessStability();

                    for (int i = 1; i < frontSolutions.size() - 1; i++) {
                        distance = frontSolutions.get(i).getCrowdingDistance()
                                + (frontSolutions.get(i + 1).getFitnessStability() - frontSolutions.get(i - 1).getFitnessStability())
                                / maxMin;
                        frontSolutions.get(i).setCrowdingDistance(distance);
                    }
                    break;
                case "tension":
                    frontSolutions.sort(Comparator.comparing(Individual::getFitnessTension));
                    frontSolutions.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
                    frontSolutions.get(frontSolutions.size() - 1).setCrowdingDistance(Double.POSITIVE_INFINITY);

                    maxMin = frontSolutions.stream().max(Comparator.comparing(Individual::getFitnessTension)).orElseThrow(NoSuchElementException::new).getFitnessTension()
                            - frontSolutions.stream().min(Comparator.comparing(Individual::getFitnessTension)).orElseThrow(NoSuchElementException::new).getFitnessTension();

                    for (int i = 1; i < frontSolutions.size() - 1; i++) {
                        distance = frontSolutions.get(i).getCrowdingDistance()
                                + (frontSolutions.get(i + 1).getFitnessTension() - frontSolutions.get(i - 1).getFitnessTension())
                                / maxMin;
                        frontSolutions.get(i).setCrowdingDistance(distance);
                    }
                    break;
            }
        }
    }

    public void crowdedComparisonOperator(List<List<Individual>> newPopulation) {
        for (List<Individual> front : newPopulation) {
            front.sort(Comparator.comparing(Individual::getCrowdingDistance).reversed());
        }
    }


    public void createOffsprings(List<Pair<Individual, Individual>> matingPool, ImmutableList<Integer> representation) {
        List<Individual> offsprings = new ArrayList<>();

        Pair<Melody, Melody> offspringsCrossover;
        for (Pair<Individual, Individual> individualIndividualPair : matingPool) {
            offspringsCrossover = Crossover.onePointCrossover(individualIndividualPair);
            offsprings.add(new Individual(Mutation.simpleMutation(offspringsCrossover.getLeft(), representation, numberOfBars, maxNumberOfNotes)));
            offsprings.add(new Individual(Mutation.simpleMutation(offspringsCrossover.getRight(), representation, numberOfBars, maxNumberOfNotes)));
        }

        this.offsprings = offsprings;
    }

    public void changePopulation() {
        List<HashMap<String, List<Integer>>> chordProgressionPattern = Representation.getChordProgressionMajor();
        BiMap<String, Integer> notesMap = Representation.getNotesMap();
        int melodyKeyValue = notesMap.get(melodyKey);
        population.addAll(offsprings);
        for (Individual individual : population) {
            individual.setFitness(criteria, chordProgressionPattern, chordProgression, melodyKeyValue);
        }
    }

    public List<List<Individual>> getFronts() {
        return fronts;
    }
}
