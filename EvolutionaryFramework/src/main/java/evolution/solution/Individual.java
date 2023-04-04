package evolution.solution;

import evolution.objective.EvaluationParameters;
import evolution.objective.Evaluator;
import evolution.music.Genome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Individual {
    final private Genome genome;
    final private HashMap<String, Double> fitness = new HashMap<>();
    private int frontRank;
    private double crowdingDistance = 0;

    public Individual(Genome genome) {
        this.genome = genome;
    }

    public Individual(Genome genome, HashMap<String, Double> fitness, int frontRank){
        for (String criterion : fitness.keySet() ){
            this.fitness.put(criterion, getFitnessByName(criterion));
        }
        this.genome = genome;
        this.frontRank = frontRank;

    }

    public Individual addCriterion(String name) {
        fitness.put(name, 0.0);
        return this;
    }
    public void removeObjective(String name) {
        this.fitness.remove(name);
    }
    public Genome getGenome() {
        return genome;
    }

    public void setFitness(List<String> criteria, EvaluationParameters evalParams){

        for(String criterion : criteria){
            this.fitness.put(criterion, Evaluator.evaluate(this, criterion, evalParams));
        }
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
        if(q.fitness.size()<2){
            int i = 0;
        }
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
