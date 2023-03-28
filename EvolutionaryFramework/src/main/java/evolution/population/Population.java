package evolution.population;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import evolution.music.Melody;
import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.objective.StabilityObjective;
import evolution.objective.TensionObjective;
import evolution.solution.Individual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Population {
    protected final int popSize;
    protected final String representationType;
    protected final List<String> criteria;
    protected final int numberOfBars;
    protected final int maxNumberOfNotes;
    protected final List<String> chordProgression;
    protected final String melodyKey;
    protected List<Individual> population;

    final protected EvaluationParameters evalParams;

    public Population(int popSize, String representationType, List<String> criteria,
                      int numberOfBars, int maxNumberOfNotes, List<String> chordProgression,
                      String melodyKey, EvaluationParameters evalParams) {
        this.popSize = popSize;
        this.representationType = representationType;
        this.criteria = criteria;
        this.numberOfBars = numberOfBars;
        this.maxNumberOfNotes = maxNumberOfNotes;
        this.chordProgression = chordProgression;
        this.melodyKey = melodyKey;
        this.evalParams = evalParams;
    }

    public void generatePopulation(ImmutableList<Integer> representation) {
        List<Individual> population = new ArrayList<>();

        for (int n = 0; n < popSize; n++) {
            Melody melody = new Melody();
            assert representation != null;
            melody.initializeMelody(representation, representationType, numberOfBars, maxNumberOfNotes);
            melody.setMelodyJFugue(maxNumberOfNotes);
            Individual individual = new Individual(melody)
                    .addCriterion("STABILITY")
                    .addCriterion("TENSION");

            individual.setFitness(this.evalParams);
            population.add(individual);
        }
        this.population = population;
    }

    public List<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual> population) {
        for (Individual individual : population) {
            individual.getGenome().setMelodyJFugue(maxNumberOfNotes);
        }
        this.population = population;
    }

}
