package evolution.objective;

import evolution.solution.Individual;

public class Evaluator {

    public static Double evaluate(Individual individual, String critName, EvaluationParameters pack) {
        if ("STABILITY".equals(critName)) {
            return StabilityObjective.evaluate(individual, pack);
        }
        return TensionObjective.evaluate(individual, pack);
    }
}
