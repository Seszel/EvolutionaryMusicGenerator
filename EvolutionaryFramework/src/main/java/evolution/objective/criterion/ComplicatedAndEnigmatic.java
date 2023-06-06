package evolution.objective.criterion;


import evolution.objective.EvaluationParameters;
import evolution.objective.Objective;
import evolution.objective.subcriterion.*;
import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

public class ComplicatedAndEnigmatic extends Objective {

    static final String name = "COMPLICATED_AND_ENIGMATIC";

    public static Double evaluate(Individual individual, EvaluationParameters pack) {

        @SuppressWarnings("unchecked")
        var criteriaRanges = (HashMap<String, Pair<Double, Double>>) pack.parameters
                .get(EvaluationParameters.ParamName.CRITERIA_RANGES);

        double fitness = 0;

        List<Double> weights = List.of(3.0, 2.0, 1.0, 1.0);

        double nonChordToneFitness = NonChordToneObjective.evaluate(individual, pack);
        double skipMotionFitness = SkipMotionObjective.evaluate(individual, pack);
        double ascendingMelodyLineFitness = AscendingMelodyLineObjective.evaluate(individual, pack);
        double nonPerfectIntervalFitness = NonPerfectIntervalObjective.evaluate(individual, pack);
        // rhytm to do
        // penalty to do

        for (int i = 0; i < weights.size(); i++){
            switch (i){
                case 0:
                    fitness += weights.get(i)*nonChordToneFitness;
                    break;
                case 1:
                    fitness += weights.get(i)*skipMotionFitness;
                    break;
                case 2:
                    fitness += weights.get(i)*ascendingMelodyLineFitness;
                    break;
                case 3:
                    fitness += weights.get(i)*nonPerfectIntervalFitness;
                    break;
            }
        }

        double min = criteriaRanges.get(name).getLeft();
        double max = criteriaRanges.get(name).getRight();
        return ( fitness - min ) / ( max - min );
    }
}
