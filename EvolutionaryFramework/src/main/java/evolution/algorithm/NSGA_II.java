package evolution.algorithm;

import com.google.common.collect.ImmutableList;
import evolution.music.Representation;
import evolution.objective.EvaluationParameters;
import evolution.operator.MatingPoolSelection;
import evolution.population.PopulationNSGA_II;
import evolution.solution.Individual;
import evolution.stats.StatsNSGA_II;
import evolution.util.Util;
import lombok.var;
import me.tongfei.progressbar.ProgressBar;
import org.apache.commons.lang3.tuple.Pair;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.Chord;
import org.jfugue.theory.ChordProgression;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NSGA_II extends AEvolutionaryAlgorithm {

    public NSGA_II(int popSize, int numberOfBars, int maxNumberOfNotes,
                   String representationType, List<String> chordProgression,
                   Pair<String, String> melodyKey, String crossoverType, Pair<String, Double> mutationType,
                   String selectionType, String matingPoolSelectionType,
                   int numberOfGenerations, int numberOfIteration, List<String> criteria,
                   Pair<Boolean, Integer> saveToJSON, String folderName) {

        super(popSize, numberOfBars, maxNumberOfNotes,
                representationType, chordProgression, melodyKey,
                crossoverType, mutationType, selectionType,
                matingPoolSelectionType, numberOfGenerations, numberOfIteration, criteria, saveToJSON, folderName);
    }

    @Override
    public void run() {

        StatsNSGA_II stats = new StatsNSGA_II("NSGA-II", popSize,
                numberOfBars, maxNumberOfNotes, representationType,
                chordProgression, melodyKey, crossoverType,
                mutationType, selectionType, matingPoolSelectionType,
                numberOfGenerations, criteria, folderName);

        System.out.println("Algorithm NSGA_II is working, iteration: " + (numberOfIteration + 1));

        var params = new EvaluationParameters("JoannaParameters");
        params.addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION_PATTERN,
                        Representation.ChordProgression(melodyKey.getRight()))
                .addParam(EvaluationParameters.ParamName.CHORD_PROGRESSION,
                        chordProgression)
                .addParam(EvaluationParameters.ParamName.MELODY_KEY,
                        melodyKey);
        PopulationNSGA_II population = new PopulationNSGA_II(
                popSize, representationType, criteria,
                numberOfBars, maxNumberOfNotes,
                chordProgression, melodyKey, params
        );
        ImmutableList<Integer> representation = Representation.getReprInt(representationType);

        population.generatePopulation(representation);
        population.generateFronts();

        List<Integer> generations = IntStream.rangeClosed(1, numberOfGenerations)
                .boxed().collect(Collectors.toList());

        for (Integer g : ProgressBar.wrap(generations, "Iteration: " + (numberOfIteration + 1))) {

            var matingPool = MatingPoolSelection.tournament(
                    10, popSize, population
            );
            population.createOffsprings(matingPool, representation, getCrossoverType(), getMutationType());

            population.changePopulation();

            population.generateFronts();

            List<List<Individual>> newPopulation = new ArrayList<>();
            int newPopulationSize = 0;

            for (List<Individual> front : population.getFronts()) {
                population.crowdingDistanceAssignment(front);
                newPopulation.add(front);
                if ((front.size() + newPopulationSize) > popSize) {
                    break;
                }
                newPopulationSize += front.size();
            }

            population.crowdedComparisonOperator(newPopulation);
            population.setPopulation(
                    new ArrayList<>(Util.flattenListOfListsStream(newPopulation).subList(0, popSize))
            );

            if (saveToJSON.getLeft() && (g % saveToJSON.getRight() == 1 || g == numberOfGenerations)) {
                stats.updateStats(g, population.getPopulation());
            }
        }

        if (saveToJSON.getLeft()) {
            stats.generateJSON(numberOfIteration);
        }
        for (Individual individual : population.getPopulation()) {

            Player player = new Player();

            Pattern chords = new ChordProgression(chordProgression.toString())
                    .setKey("C")
                    .allChordsAs("$0w $1w $2w $3w")
                    .getPattern()
                    .setVoice(0)
                    .setInstrument("Piano")
                    .setTempo(90);

            Pattern pattern = new Pattern("X[Volume]=13000" + individual.getGenome().getMelodyJFugue())
                    .setTempo(90)
                    .setInstrument("Piano")
                    .setVoice(1);
            player.play(chords, pattern);
        }

        System.out.println("Nsga_II algorithm ended work, iteration: " + (numberOfIteration + 1));
    }

}
