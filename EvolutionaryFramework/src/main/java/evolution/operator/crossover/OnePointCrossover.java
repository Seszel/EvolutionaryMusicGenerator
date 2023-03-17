package evolution.operator.crossover;

import com.sun.tools.javac.util.Pair;
import evolution.helper.Helper;
import evolution.music.Melody;
import evolution.solution.Individual;

import java.util.ArrayList;
import java.util.List;

public class OnePointCrossover implements ICrossover {
    public static Pair<Melody,Melody> crossover(Individual parent1, Individual parent2) {
        int idx;
        int maxNumberOfNotes = parent1.getGenome().getMaxNumberOfNotes();
        int numberOfBars = parent1.getGenome().getNumberOfBars();
        String representationType = parent1.getGenome().getRepresentationType();
        ArrayList<ArrayList<Integer>> offspringMelody1 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> offspringMelody2 = new ArrayList<>();
        for (int i = 0; i < numberOfBars; i++) {
            idx = Helper.getRandomNumber(0, maxNumberOfNotes);
            ArrayList<Integer> offspring1 = new ArrayList<>(parent1.getGenome().getMelody().get(i).subList(0, idx));
            offspring1.addAll(parent1.getGenome().getMelody().get(i).subList(idx, maxNumberOfNotes));

            ArrayList<Integer> offspring2 = new ArrayList<>(parent1.getGenome().getMelody().get(i).subList(0, idx));
            offspring2.addAll(parent2.getGenome().getMelody().get(i).subList(idx, maxNumberOfNotes));

            offspringMelody1.add(offspring1);
            offspringMelody2.add(offspring2);

        }

        Melody melody1 = new Melody(numberOfBars,maxNumberOfNotes,representationType);
        Melody melody2 = new Melody(numberOfBars, maxNumberOfNotes, representationType);
        melody1.setMelody(offspringMelody1);
        melody2.setMelody(offspringMelody2);

        return new Pair<>(melody1, melody2);
    }
}
