package evolution.solution;

import evolution.objective.Objective;
import evolution.util.Util;
import evolution.music.Melody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Individual {
    private Melody genome;
    private ArrayList<Double> fitness;
    private ArrayList<Objective<Double>> objectives;
    private int frontRank;
    private double crowdingDistance = 0;

    public Individual(Melody genome) {
        this.genome = genome;
        this.fitness = new ArrayList<>();
        this.objectives = new ArrayList<>();
    }

    public Individual addCriterion(Objective<Double> objective) {
        this.objectives.add(objective);
        this.fitness.add(0.0);

        return this;
    }
    public void removeObjective(String name) {
        // TODO: 22.03.2023  
    }
    public Melody getGenome() {
        return genome;
    }

    public void setFitness(){

        for(int i = 0; i < objectives.size(); i ++){
            this.fitness.set(i, this.objectives.get(i).evaluate(this));
        }
    }

    public ArrayList<Double> getFitness(){
        return fitness;
    }
    public Double getFitnessStability(){
        return fitness.get(0);
    }

    public Double getFitnessTension(){
        return fitness.get(1);
    }

    public void setFrontRank(int frontRank) {
        this.frontRank = frontRank;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

    public boolean dominates(Individual q) {
        if (fitness.get(0) > q.fitness.get(0) && fitness.get(1) >= q.fitness.get(1)) {
            return true;
        }
        return fitness.get(0) >= q.fitness.get(0) && fitness.get(1) > q.fitness.get(1);
    }

    public int getFrontRank() {
        return frontRank;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }
}
