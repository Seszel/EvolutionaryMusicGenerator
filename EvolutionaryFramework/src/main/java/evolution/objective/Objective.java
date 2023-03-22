package evolution.objective;

import evolution.solution.Individual;

public abstract class Objective<T>{
    protected final String name;
    protected final boolean maximize;

    protected Objective(String name, boolean maximize) {
        this.name = name;
        this.maximize = maximize;
    }

    String getName() { return name; }
    boolean getType() { return maximize; }

    public abstract T evaluate(Individual individual);
}
