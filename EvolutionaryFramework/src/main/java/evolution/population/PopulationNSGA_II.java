package evolution.population;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Pair;
import evolution.music.Melody;
import evolution.music.Representation;
import evolution.operator.crossover.OnePointCrossover;
import evolution.operator.mutatation.SimpleMutation;
import evolution.solution.Individual;

import java.util.*;

public class PopulationNSGA_II extends Population {

    private ArrayList<ArrayList<Individual>> fronts;
    private ArrayList<Individual> offsprings;

    public PopulationNSGA_II(int popSize, String representationType, List<String> criteria, int numberOfBars, int maxNumberOfNotes, List<String> chordProgression, String melodyKey) {
        super(popSize, representationType, criteria, numberOfBars, maxNumberOfNotes, chordProgression, melodyKey);
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

    public void crowdingDistanceAssignment() {

        for (ArrayList<Individual> frontSolutions : fronts) {

            ArrayList<Double> frontDistances = new ArrayList<>(Collections.nCopies(frontSolutions.size(), 0.0));
            frontDistances.set(0, Double.POSITIVE_INFINITY);
            frontDistances.set(frontSolutions.size() - 1, Double.POSITIVE_INFINITY);

            frontSolutions.sort(Comparator.comparing(Individual::getFitnessStability));
            double maxMin1 = frontSolutions.stream().max(Comparator.comparing(Individual::getFitnessStability)).orElseThrow(NoSuchElementException::new).getFitnessStability()
                    - frontSolutions.stream().min(Comparator.comparing(Individual::getFitnessStability)).orElseThrow(NoSuchElementException::new).getFitnessStability();
            for (int i = 1; i < frontSolutions.size() - 1; i++) {
                double distance1 = frontDistances.get(i)
                        + (frontSolutions.get(i + 1).getFitnessStability() - frontSolutions.get(i - 1).getFitnessStability())
                        / maxMin1;
                frontDistances.set(i, distance1);
            }
            frontSolutions.sort(Comparator.comparing(Individual::getFitnessTension));
            double maxMin2 = frontSolutions.stream().max(Comparator.comparing(Individual::getFitnessTension)).orElseThrow(NoSuchElementException::new).getFitnessTension()
                    - frontSolutions.stream().min(Comparator.comparing(Individual::getFitnessTension)).orElseThrow(NoSuchElementException::new).getFitnessTension();
            for (int i = 1; i < frontSolutions.size() - 1; i++) {
                double distance2 = frontDistances.get(i)
                        + (frontSolutions.get(i + 1).getFitnessTension() - frontSolutions.get(i - 1).getFitnessTension())
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


    public void createOffsprings(ArrayList<Pair<Individual,Individual>> matingPool, ImmutableList<Integer> representation){
        ArrayList<Individual> offsprings = new ArrayList<>();

        Pair<Melody, Melody> offspringsCrossover;
        for (Pair<Individual, Individual> individualIndividualPair : matingPool) {
            offspringsCrossover = OnePointCrossover.crossover(individualIndividualPair, numberOfBars, maxNumberOfNotes);
            offsprings.add(new Individual(SimpleMutation.mutation(offspringsCrossover.fst, representation, numberOfBars, maxNumberOfNotes)));
            offsprings.add(new Individual(SimpleMutation.mutation(offspringsCrossover.snd, representation, numberOfBars, maxNumberOfNotes)));
        }

        this.offsprings = offsprings;
    }

    public void changePopulation(){
        ArrayList<HashMap<String, List<Integer>>> chordProgressionPattern = Representation.getChordProgressionMajor();
        BiMap<String, Integer> notesMap = Representation.getNotesMap();
        int melodyKeyValue = notesMap.get(melodyKey);
        population.addAll(offsprings);
        for (Individual individual:population) {
            individual.setFitness(criteria,chordProgressionPattern, chordProgression, melodyKeyValue);
        }
    }

    public ArrayList<ArrayList<Individual>> getFronts() {
        return fronts;
    }
}
