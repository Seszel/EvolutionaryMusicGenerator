package evolution.objective.criterion;


import evolution.objective.EvaluationParameters;
import evolution.objective.Objective;
import evolution.objective.subcriterion.*;
import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

public class ComplicatedAndEnigmatic extends Objective {

    static final String name = "COMPLICATED_AND_ENIGMATIC";

    public static Double evaluate(Individual individual, EvaluationParameters pack) {

        @SuppressWarnings("unchecked")
        var criteriaRanges = (HashMap<String, Pair<Double, Double>>) pack.parameters
                .get(EvaluationParameters.ParamName.CRITERIA_RANGES);

        @SuppressWarnings("unchecked")
        var weights = (HashMap<String, Double>) pack.parameters
                .get(EvaluationParameters.ParamName.WEIGHTS);

        double fitness = 0;

        double nonChordToneFitness = NonChordToneObjective.evaluate(individual, pack);
        double skipMotionFitness = SkipMotionObjective.evaluate(individual, pack);
        double ascendingMelodyLineFitness = AscendingMelodyLineObjective.evaluate(individual, pack);
        double nonPerfectIntervalFitness = NonPerfectIntervalObjective.evaluate(individual, pack);
        double complicatedRhythmFitness = ComplicatedRhythmObjective.evaluate(individual,pack);
        double undesirablePropertiesMelodyFitness = UndesirablePropertiesMelodyObjective.evaluate(individual,pack);

        fitness += weights.get("NON_CHORD_TONE") * nonChordToneFitness
                + weights.get("SKIP_MOTION") * skipMotionFitness
                + weights.get("ASCENDING_MELODY_LINE") * ascendingMelodyLineFitness
                + weights.get("NON_PERFECT_INTERVAL") * nonPerfectIntervalFitness
                + weights.get("COMPLICATED_RHYTHM") * complicatedRhythmFitness
                + weights.get("UNDESIRABLE_PROPERTIES_MELODY") * undesirablePropertiesMelodyFitness;

        double min = criteriaRanges.get(name).getLeft();
        double max = criteriaRanges.get(name).getRight();
        return ( fitness - min ) / ( max - min );

//        return fitness;
    }
}
