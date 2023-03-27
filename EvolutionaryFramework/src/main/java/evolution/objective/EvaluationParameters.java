package evolution.objective;

import evolution.music.Representation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EvaluationParameters extends Representation {

    public final String name;
    public enum ParamName {
        CHORD_PROGRESSION_PATTERN,
        MELODY_KEY_VALUE,
        CHORD_PROGRESSION
    }
    public HashMap<ParamName, Object> parameters;

    public EvaluationParameters(String name) {
        this.name = name;
    }

    public EvaluationParameters addParam(ParamName name, Object val) {
        this.parameters.put(name, val);
        return this;
    }

    void removeParam(ParamName name) {
        this.parameters.remove(name);
    }

}
