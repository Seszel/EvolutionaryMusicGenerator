package evolution.objective;

import evolution.objective.criterion.*;
import evolution.objective.subcriterion.*;
import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

public class Evaluator {

    public static HashMap<String, Pair<Double, Double>> evaluate(Individual individual,
                                                                 List<String> criterionNames,
                                                                 EvaluationParameters pack) {

        HashMap<String, Pair<Double, Double>> eval = new HashMap<>();
        for(String criterion : criterionNames){
            eval.put(criterion, evaluate(individual, criterion, pack));
        }

        return eval;
    }

    public static Pair<Double, Double> evaluate(Individual individual,
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
            case "SIMPLE_RHYTHM":
                return SimpleRhythmObjective.evaluate(individual, pack);
            case "COMPLICATED_RHYTHM":
                return ComplicatedRhythmObjective.evaluate(individual, pack);
            case "UNDESIRABLE_PROPERTIES_MELODY":
                return UndesirablePropertiesMelodyObjective.evaluate(individual, pack);
            case "SIMPLE_AND_OBVIOUS":
                return SimpleAndObvious.evaluate(individual, pack);
            case "COMPLICATED_AND_ENIGMATIC":
                return ComplicatedAndEnigmatic.evaluate(individual, pack);
            case "SKIP":
                return Skip.evaluate(individual, pack);
            case "STEP":
                return Step.evaluate(individual, pack);
            case "ASCENDING":
                return Ascending.evaluate(individual, pack);
            case "DESCENDING":
                return Descending.evaluate(individual, pack);
            default:
                return null;
        }
    }
}
