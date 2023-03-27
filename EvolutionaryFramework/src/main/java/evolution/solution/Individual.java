package evolution.solution;

import evolution.objective.EvaluationParameters;
import evolution.objective.Evaluator;
import evolution.music.Melody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Individual {
    private Melody genome;
    private HashMap<String, Double> fitness;
    private int frontRank;
    private double crowdingDistance = 0;

    public Individual(Melody genome) {
        this.genome = genome;
    }

    public Individual addCriterion(String name) {
        this.fitness.put(name, 0.0);
        return this;
    }
    public void removeObjective(String name) {
        this.fitness.remove(name);
    }
    public Melody getGenome() {
        return genome;
    }

    public void setFitness(EvaluationParameters evalParams){

        this.fitness.forEach((criterion, val) -> {
            Evaluator.evaluate(this, criterion, evalParams);
        });
    }

    public HashMap<String, Double> getFitness(){
        return fitness;
    }

     public Double getFitnessByName(String name) {

        return fitness.get(name);
    }

    public void setFrontRank(int frontRank) {
        this.frontRank = frontRank;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

    public boolean dominates(Individual q) {
        boolean strictlyBetter = false;
        for(Map.Entry<String, Double> c : fitness.entrySet()){
            if(c.getValue() < q.getFitnessByName(c.getKey())){
                return false;
            }
            if(c.getValue() > q.getFitnessByName(c.getKey())){
                strictlyBetter = true;
            }
        }
        return strictlyBetter;
    }

    public int getFrontRank() {
        return frontRank;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }
}
