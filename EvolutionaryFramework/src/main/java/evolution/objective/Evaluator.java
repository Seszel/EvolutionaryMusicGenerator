package evolution.objective;

import evolution.objective.subcriterion.*;
import evolution.solution.Individual;
import lombok.var;

import java.util.HashMap;
import java.util.List;

public class Evaluator {

    public static HashMap<String, Double> evaluate(Individual individual,
                                  List<String> criterionNames,
                                  EvaluationParameters pack) {

        var eval = new HashMap<String, Double>();
        for(String criterion : criterionNames){
            eval.put(criterion, evaluate(individual, criterion, pack));
        }

        return eval;
    }

    public static Double evaluate(Individual individual,
                                  String criterion,
                                  EvaluationParameters pack) {
        switch(criterion) {
            case "STABILITY":
                return StabilityObjective.evaluate(individual, pack);
            case "TENSION":
                return TensionObjective.evaluate(individual, pack);
            case "CHORD_TONE":
                return ChordToneObjective.evaluate(individual, pack);
            case "NON_CHORD_TONE":
                return NonChordToneObjective.evaluate(individual, pack);
            case "STEP_MOTION":
                return StepMotionObjective.evaluate(individual, pack);
            case "SKIP_MOTION":
                return SkipMotionObjective.evaluate(individual, pack);
            case "PERFECT_INTERVAL":
                return PerfectIntervalObjective.evaluate(individual, pack);
            case "NON_PERFECT_INTERVAL":
                return NonPerfectIntervalObjective.evaluate(individual, pack);
            case "DESCENDING_MELODY_LINE":
                return DescendingMelodyLineObjective.evaluate(individual, pack);
            case "ASCENDING_MELODY_LINE":
                return AscendingMelodyLineObjective.evaluate(individual, pack);
            default:
                return null;
        }
    }
}
