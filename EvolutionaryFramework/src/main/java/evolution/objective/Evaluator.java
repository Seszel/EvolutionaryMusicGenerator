package evolution.objective;

import evolution.solution.Individual;
import lombok.var;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

        if ("STABILITY".equals(criterion)) {
            return StabilityObjective.evaluate(individual, pack);
        }
        return TensionObjective.evaluate(individual, pack);
    }
}
