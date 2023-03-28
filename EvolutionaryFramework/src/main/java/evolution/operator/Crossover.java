package evolution.operator;

import evolution.music.Melody;
import evolution.solution.Individual;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Crossover {
    public static Pair<Melody, Melody> onePointCrossover(Pair<Individual, Individual> parents) {
        List<List<Integer>> offspringMelody1 = new ArrayList<>();
        List<List<Integer>> offspringMelody2 = new ArrayList<>();

        int idx;

//        for (int i = 0; i < parents.getLeft().getGenome().getMelody().size(); i++) {
//            idx = Util.getRandomNumber(0, parents.getLeft().getGenome().getMelody().get(i).size());
//            List<Integer> offspring1 = new ArrayList<>(parents.getLeft().getGenome().getMelody().get(i).subList(0, idx));
//            offspring1.addAll(parents.getRight().getGenome().getMelody().get(i).subList(idx, offspringMelody1.get(i).size()));
//
//            List<Integer> offspring2 = new ArrayList<>(parents.getLeft().getGenome().getMelody().get(i).subList(0, idx));
//            offspring2.addAll(parents.getRight().getGenome().getMelody().get(i).subList(idx, offspringMelody1.get(i).size()));
//
//            offspringMelody1.add(offspring1);
//            offspringMelody2.add(offspring2);
//
//        }

        Melody melody1 = new Melody();
        Melody melody2 = new Melody();
        melody1.setMelody(offspringMelody1);
        melody2.setMelody(offspringMelody2);

        return new MutablePair<>(melody1, melody2);
    }
}
