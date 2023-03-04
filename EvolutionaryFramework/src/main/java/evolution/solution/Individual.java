package evolution.solution;

import evolution.music.Melody;

public class Individual<T> {
    private final int id;
    private Melody<T> genome;
    private double fitness1 = 0;
    private double fitness2 = 0;

    public Individual(int id, Melody<T> genome) {
        this.id = id;
        this.genome = genome;
    }

    public int getId() {
        return id;
    }

    public Melody<T> getGenome() {
        return genome;
    }

    public void setGenome(Melody<T> genome) {
        this.genome = genome;
    }

    public double getFitness1() {
        return fitness1;
    }

    public void setFitness1(double fitness1) {
        this.fitness1 = fitness1;
    }

    public double getFitness2() {
        return fitness2;
    }

    public void setFitness2(double fitness2) {
        this.fitness2 = fitness2;
    }
}
