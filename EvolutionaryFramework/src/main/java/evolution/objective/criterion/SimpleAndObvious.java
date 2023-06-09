package evolution.objective.criterion;

import evolution.objective.EvaluationParameters;
import evolution.objective.Objective;
import evolution.objective.subcriterion.*;
import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

public class SimpleAndObvious extends Objective {

    static final String name = "SIMPLE_AND_OBVIOUS";

    public static Double evaluate(Individual individual, EvaluationParameters pack) {

        @SuppressWarnings("unchecked")
        var criteriaRanges = (HashMap<String, Pair<Double, Double>>) pack.parameters
                .get(EvaluationParameters.ParamName.CRITERIA_RANGES);

        @SuppressWarnings("unchecked")
        var weights = (HashMap<String, Double>) pack.parameters
                .get(EvaluationParameters.ParamName.WEIGHTS);

        double fitness = 0;


        double chordToneFitness = ChordToneObjective.evaluate(individual, pack);
        double stepMotionFitness = StepMotionObjective.evaluate(individual, pack);
        double descendingMelodyLineFitness = DescendingMelodyLineObjective.evaluate(individual, pack);
        double perfectIntervalFitness = PerfectIntervalObjective.evaluate(individual, pack);
        double simpleRhythmFitness = SimpleRhythmObjective.evaluate(individual,pack);
        double undesirablePropertiesMelodyFitness = UndesirablePropertiesMelodyObjective.evaluate(individual,pack);

        fitness += weights.get("CHORD_TONE") * chordToneFitness
                + weights.get("STEP_MOTION") * stepMotionFitness
                + weights.get("DESCENDING_MELODY_LINE") * descendingMelodyLineFitness
                + weights.get("PERFECT_INTERVAL") * perfectIntervalFitness
                + weights.get("SIMPLE_RHYTHM") * simpleRhythmFitness
                + weights.get("UNDESIRABLE_PROPERTIES_MELODY") * undesirablePropertiesMelodyFitness;


//        double min = criteriaRanges.get(name).getLeft();
//        double max = criteriaRanges.get(name).getRight();
//        return ( fitness - min ) / ( max - min );

        return fitness;
    }

}
