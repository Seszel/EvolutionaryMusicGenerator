package evolution.objective.criterion;

import evolution.objective.EvaluationParameters;
import evolution.objective.Objective;
import evolution.objective.subcriterion.SkipMotionObjective;
import evolution.objective.subcriterion.UndesirablePropertiesMelodyObjective;
import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

public class Skip extends Objective {

    static final String name = "SKIP";

    public static Pair<Double, Double> evaluate(Individual individual, EvaluationParameters pack) {

        @SuppressWarnings("unchecked")
        var criteriaRanges = (HashMap<String, Pair<Double, Double>>) pack.parameters
                .get(EvaluationParameters.ParamName.CRITERIA_RANGES);

        @SuppressWarnings("unchecked")
        var weights = (HashMap<String, Double>) pack.parameters
                .get(EvaluationParameters.ParamName.WEIGHTS);

        double fitness = 0;
        double penalty = 0;

        Pair<Double, Double> skipMotionFitness = SkipMotionObjective.evaluate(individual, pack);
        Pair<Double, Double> undesirablePropertiesMelodyFitness = UndesirablePropertiesMelodyObjective.evaluate(individual,pack);

        fitness += weights.get("SKIP_MOTION") * skipMotionFitness.getKey()
                + weights.get("UNDESIRABLE_PROPERTIES_MELODY") * undesirablePropertiesMelodyFitness.getKey();


        double min = criteriaRanges.get(name).getLeft();
        double max = criteriaRanges.get(name).getRight();

        penalty = undesirablePropertiesMelodyFitness.getKey() * weights.get("UNDESIRABLE_PROPERTIES_MELODY") / max;

        return new ImmutablePair<>(( fitness - min ) / ( max - min ), penalty);

//        return new ImmutablePair<>(fitness, penalty);

    }
}
