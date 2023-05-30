package evolution.operator;

import evolution.music.Genome;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Crossover {
    public static Pair<Genome, Genome> crossover(double crossoverProbability, List<Pair<String, Double>> crossoverType, Pair<Genome, Genome> parents) {
        Random randomObj = new Random();

        double maxDouble = crossoverType.stream()
                .mapToDouble(Pair::getValue)
                .max()
                .orElse(Double.MIN_VALUE);

        List<Pair<String, Double>> mutableList = new ArrayList<>(crossoverType);
        mutableList.sort(Comparator.comparing(Pair::getValue));
        mutableList.replaceAll(pair -> new ImmutablePair<>(pair.getKey(), pair.getValue() / maxDouble));

        double randomProbability = randomObj.nextDouble();
        String crossoverName = "";

        for (int i=0; i<mutableList.size(); i++){
            if (randomProbability < mutableList.get(i).getRight()){
                crossoverName = mutableList
                        .subList(i,mutableList.size())
                        .get(new Random().nextInt(mutableList.size()-i))
                        .getLeft();
                break;
            }
        }

        double randomCrossoverProbability = randomObj.nextDouble();
        if (randomCrossoverProbability > crossoverProbability){
            crossoverName = "NO_CROSSOVER";
        }

        switch (crossoverName) {
            case "ONE_POINT_CROSSOVER":
                return onePointCrossover(parents);
            case "TWO_POINT_CROSSOVER":
                return twoPointCrossover(parents);
            case "NO_CROSSOVER":
            default:
                return parents;
        }
    }

    public static Pair<Genome, Genome> onePointCrossover(Pair<Genome, Genome> parents) {
        List<List<Integer>> offspringMelody1 = new ArrayList<>();
        List<List<Integer>> offspringMelody2 = new ArrayList<>();

        int idx;

        for (int i = 0; i < parents.getLeft().getMelody().size(); i++) {
            idx = Util.getRandomNumber(0, parents.getLeft().getMelody().get(i).size() - 1);
            List<Integer> offspring1 = new ArrayList<>(parents.getLeft().getMelody().get(i).subList(0, idx));
            offspring1.addAll(parents.getRight().getMelody().get(i).subList(idx, parents.getLeft().getMelody().get(i).size()));

            List<Integer> offspring2 = new ArrayList<>(parents.getRight().getMelody().get(i).subList(0, idx));
            offspring2.addAll(parents.getLeft().getMelody().get(i).subList(idx, parents.getRight().getMelody().get(i).size()));

            offspringMelody1.add(offspring1);
            offspringMelody2.add(offspring2);

        }

        Genome genome1 = new Genome();
        Genome genome2 = new Genome();
        genome1.setMelody(offspringMelody1);
        genome2.setMelody(offspringMelody2);

        return new MutablePair<>(genome1, genome2);
    }

    public static Pair<Genome, Genome> twoPointCrossover(Pair<Genome, Genome> parents) {
        List<List<Integer>> offspringMelody1 = new ArrayList<>();
        List<List<Integer>> offspringMelody2 = new ArrayList<>();

        int idx1, idx2;

        for (int i = 0; i < parents.getLeft().getMelody().size(); i++) {
            idx1 = Util.getRandomNumber(0, parents.getLeft().getMelody().get(i).size() - 2);
            do {
                idx2 = Util.getRandomNumber(idx1 + 1, parents.getLeft().getMelody().get(i).size() - 1);
            } while (idx1 == idx2);

            List<Integer> offspring1 = new ArrayList<>(parents.getLeft().getMelody().get(i).subList(0, idx1));
            offspring1.addAll(parents.getRight().getMelody().get(i).subList(idx1, idx2));
            offspring1.addAll(parents.getLeft().getMelody().get(i).subList(idx2, parents.getLeft().getMelody().get(i).size()));

            List<Integer> offspring2 = new ArrayList<>(parents.getRight().getMelody().get(i).subList(0, idx1));
            offspring2.addAll(parents.getLeft().getMelody().get(i).subList(idx1, idx2));
            offspring2.addAll(parents.getRight().getMelody().get(i).subList(idx2, parents.getRight().getMelody().get(i).size()));

            offspringMelody1.add(offspring1);
            offspringMelody2.add(offspring2);

        }

        Genome genome1 = new Genome();
        Genome genome2 = new Genome();
        genome1.setMelody(offspringMelody1);
        genome2.setMelody(offspringMelody2);

        return new MutablePair<>(genome1, genome2);
    }

}
