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

        double fitness = 0;

        List<Double> weights = List.of(3.0, 2.0, 1.0, 1.0, 0.5, 100.0);

        double chordToneFitness = ChordToneObjective.evaluate(individual, pack);
        double stepMotionFitness = StepMotionObjective.evaluate(individual, pack);
        double descendingMelodyLineFitness = DescendingMelodyLineObjective.evaluate(individual, pack);
        double perfectIntervalFitness = PerfectIntervalObjective.evaluate(individual, pack);
        double simpleRhythmFitness =SimpleRhythmObjective.evaluate(individual,pack);
        double undesirablePropertiesMelodyFitness = UndesirablePropertiesMelodyObjective.evaluate(individual,pack);

        for (int i = 0; i < weights.size(); i++){
            switch (i){
                case 0:
                    fitness += weights.get(i)*chordToneFitness;
                    break;
                case 1:
                    fitness += weights.get(i)*stepMotionFitness;
                    break;
                case 2:
                    fitness += weights.get(i)*descendingMelodyLineFitness;
                    break;
                case 3:
                    fitness += weights.get(i)*perfectIntervalFitness;
                    break;
                case 4:
                    fitness += weights.get(i)*simpleRhythmFitness;
                    break;
                case 5:
                    fitness += weights.get(i)*undesirablePropertiesMelodyFitness;
                    break;
            }
        }

        double min = criteriaRanges.get(name).getLeft();
        double max = criteriaRanges.get(name).getRight();
        return ( fitness - min ) / ( max - min );
    }

}
