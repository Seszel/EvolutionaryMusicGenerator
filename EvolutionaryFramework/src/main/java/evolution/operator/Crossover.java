package evolution.operator;

import evolution.music.Genome;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Crossover {
    public static Pair<Genome, Genome> onePointCrossover(Pair<Genome, Genome> parents) {
        List<List<Integer>> offspringMelody1 = new ArrayList<>();
        List<List<Integer>> offspringMelody2 = new ArrayList<>();

        int idx;

        for (int i = 0; i < parents.getLeft().getMelody().size(); i++) {
            idx = Util.getRandomNumber(0, parents.getLeft().getMelody().get(i).size()-1);
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
}
