package evolution.solution;

import com.sun.tools.javac.util.List;
import evolution.util.Util;
import evolution.music.Melody;

import java.util.ArrayList;
import java.util.HashMap;

public class Individual {
    private Melody genome;
    private ArrayList<Double> fitness;
    private int frontRank;
    private double crowdingDistance = 0;

    public Individual(Melody genome) {
        this.genome = genome;
    }

    public Melody getGenome() {
        return genome;
    }

    public void setFitness(List<String> criteria, ArrayList<HashMap<String, List<Integer>>> chordProgressionPattern, List<String> chordProgression, int melodyKeyValue){
        ArrayList<Double> fitness = new ArrayList<>();
        fitness.add(0.0);
        fitness.add(0.0);
        for (int c=0; c< criteria.size(); c++) {
            switch (criteria.get(c)){
                case "stability":
                    fitness.set(c,setFitnessStability(chordProgressionPattern, chordProgression, melodyKeyValue));
                    break;
                case "tension":
                    fitness.set(c,setFitnessTension(chordProgressionPattern, chordProgression, melodyKeyValue));
                    break;
            }
        }
        this.fitness = fitness;
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

    public double setFitnessStability(ArrayList<HashMap<String, List<Integer>>> chordProgressionPattern, List<String> chordProgression, int melodyKeyValue) {
        int fitness = 0;
        ArrayList<ArrayList<Integer>> melody = genome.getMelody();

        //CHORD NOTES
        int count = 1;
        int lastNoteValue = 0;
        int noteValue;
        for (int i = 0; i < melody.size(); i++) {
            for (int j = 0; j < melody.get(i).size(); j++) {
                noteValue = melody.get(i).get(j);
                if (noteValue != 0 && noteValue != -1) {
                    if (chordProgressionPattern.get(0).get(chordProgression.get(i)).contains((noteValue - melodyKeyValue) % 12)) {
                        fitness += 30;
                        if (lastNoteValue != 0) {
                            if (Math.abs(noteValue - lastNoteValue) <= 2) {
                                fitness += 10;
                            }
                        }
                    } else if (chordProgressionPattern.get(1).get(chordProgression.get(i)).contains((noteValue - melodyKeyValue) % 12)) {
                        fitness -= 10;
                    } else if (chordProgressionPattern.get(2).get(chordProgression.get(i)).contains((noteValue - melodyKeyValue) % 12)) {
                        fitness -= 20;
                    } else {
                        fitness -= 30;
                    }
                    count = 1;
                    lastNoteValue = noteValue;
                } else if (noteValue == 0) {
                    count += 1;
                } else {
                    count = 1;
                }
            }
        }

        // MOTION
        ArrayList<Integer> melodyArray = Util.flattenListOfListsStream(melody);
        melodyArray.removeAll(List.of(-1, 0));
        int countStepwise = 0;
        int countLeap = -1;
        for (int i = 1; i < melodyArray.size(); i++) {
            if (Math.abs(melodyArray.get(i - 1) - melodyArray.get(i)) <= 4) {
                countStepwise += 1;
            } else {
                if (countLeap > -1) {
                    if (countStepwise > countLeap) {
                        countLeap = countStepwise;
                    }
                } else {
                    countLeap = 0;
                }
                countStepwise = 0;
            }
        }
        if (countStepwise == melodyArray.size()) {
            if (melodyArray.get(0) > melodyArray.get(melodyArray.size() - 1)) {
                fitness += 20;
            } else if (melodyArray.get(0) < melodyArray.get(melodyArray.size() - 1)) {
                fitness += 15;
            }
        }
        else if (countLeap > 1 && Math.abs(melodyArray.get(0) - melodyArray.get(melodyArray.size() - 1)) > 0) {
            fitness += 20;
        }

        //INTERVAL
        List<Integer> perfectIntervals = List.of(0, 12, 5, 7);
        for (int i = 1; i < melodyArray.size(); i++) {
            if (perfectIntervals.contains(Math.abs(melodyArray.get(i - 1) - melodyArray.get(i)))) {
                fitness += 10;
            } else if (Math.abs(melodyArray.get(i - 1) - melodyArray.get(i)) > 12) {
                fitness -= 20;
            }
        }


        // punishment for to much of notes
        for (Integer integer : melodyArray) {
            if (integer != 0) {
                fitness -= 15;
            } else {
                fitness += 5;
            }
        }

        return fitness;
    }

    public double setFitnessTension(ArrayList<HashMap<String, List<Integer>>> chordProgressionPattern,List<String> chordProgression, int melodyKeyValue) {

        int fitness = 0;
        ArrayList<ArrayList<Integer>> melody = genome.getMelody();

        //CHORD NOTES
        int count = 1;
        int lastNoteValue = 0;
        int noteValue;
        for (int i = 0; i < melody.size(); i++) {
            for (int j = 0; j < melody.get(i).size(); j++) {
                noteValue = melody.get(i).get(j);
                if (noteValue != 0 && noteValue != -1) {
                    if (chordProgressionPattern.get(0).get(chordProgression.get(i)).contains((noteValue - melodyKeyValue) % 12)) {
                        fitness -= 5;
                        if (lastNoteValue != 0) {
                            if (Math.abs(noteValue - lastNoteValue) <= 2) {
                                fitness += 30;
                            }
                        }
                    } else if (chordProgressionPattern.get(1).get(chordProgression.get(i)).contains((noteValue - melodyKeyValue) % 12)) {
                        fitness += 20;
                    } else if (chordProgressionPattern.get(2).get(chordProgression.get(i)).contains((noteValue - melodyKeyValue) % 12)) {
                        fitness += 5;
                    } else {
                        fitness -= 10;
                    }
                    count = 1;
                    lastNoteValue = noteValue;
                } else if (noteValue == 0) {
                    count += 1;
                } else {
                    count = 1;
                }
            }
        }

        // MOTION
        ArrayList<Integer> melodyArray = Util.flattenListOfListsStream(melody);
        melodyArray.removeAll(List.of(-1, 0));
        int countStepwise = 0;
        int countLeap = -1;
        for (int i = 1; i < melodyArray.size(); i++) {
            if (Math.abs(melodyArray.get(i - 1) - melodyArray.get(i)) <= 4) {
                countStepwise += 1;
            } else {
                if (countLeap > -1) {
                    if (countStepwise > countLeap) {
                        countLeap = countStepwise;
                    }
                } else {
                    countLeap = 0;
                }
                countStepwise = 0;
            }
        }
        if (countStepwise == melodyArray.size()) {
            if (melodyArray.get(0) > melodyArray.get(melodyArray.size() - 1)) {
                fitness += 15;
            } else if (melodyArray.get(0) < melodyArray.get(melodyArray.size() - 1)) {
                fitness += 20;
            }
        }
        else if (countLeap > 1 && Math.abs(melodyArray.get(0) - melodyArray.get(melodyArray.size() - 1)) > 0) {
            fitness += 20;
        }

        //INTERVAL
        List<Integer> perfectIntervals = List.of(0, 12, 5, 7);
        for (int i = 1; i < melodyArray.size(); i++) {
            if (perfectIntervals.contains(Math.abs(melodyArray.get(i - 1) - melodyArray.get(i)))) {
                fitness -= 5;
            } else if (Math.abs(melodyArray.get(i - 1) - melodyArray.get(i)) > 12) {
                fitness -= 20;
            }
        }

//         punishment for to much of notes
        for (Integer integer : melodyArray) {
            if (integer != 0) {
                fitness -= 15;
            } else {
                fitness += 5;
            }
        }

        return fitness;

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
