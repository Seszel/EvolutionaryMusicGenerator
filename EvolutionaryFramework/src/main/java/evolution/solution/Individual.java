package evolution.solution;

import com.google.common.collect.BiMap;
import com.sun.tools.javac.util.List;
import evolution.helper.Helper;
import evolution.music.Melody;

import java.util.ArrayList;
import java.util.HashMap;

public class Individual {
    private Melody genome;
    private double fitness1 = 0;
    private double fitness2 = 0;
    private int frontRank;
    private double crowdingDistance = Double.POSITIVE_INFINITY;

    public Individual(Melody genome) {
        this.genome = genome;
    }

    public Melody getGenome() {
        return genome;
    }

    public void setFitness1(ArrayList<HashMap<String, List<Integer>>> chordProgressionPattern, List<String> chordProgression, int melodyKeyValue) {
        int fitness = 0;
        ArrayList<ArrayList<Integer>> melody = genome.getMelody();

        //CHORD NOTES
        int count = 1;
        int lastNoteValue = 0;
        int noteValue;
        for (int i = 0; i < melody.size(); i++) {
            for (int j = 0; j < genome.getMaxNumberOfNotes(); j++) {
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
        ArrayList<Integer> melodyArray = Helper.flattenListOfListsStream(melody);
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
        } else if (countLeap > 1 && Math.abs(melodyArray.get(0) - melodyArray.get(melodyArray.size() - 1)) > 0) {
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

        this.fitness1 = fitness;
    }

    public double getFitness1() {
        return fitness1;
    }

    public void setFitness2(ArrayList<HashMap<String, List<Integer>>> chordProgressionPattern,List<String> chordProgression, int melodyKeyValue) {

        int fitness = 0;
        ArrayList<ArrayList<Integer>> melody = genome.getMelody();

        //CHORD NOTES
        int count = 1;
        int lastNoteValue = 0;
        int noteValue;
        for (int i = 0; i < melody.size(); i++) {
            for (int j = 0; j < genome.getMaxNumberOfNotes(); j++) {
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
        ArrayList<Integer> melodyArray = Helper.flattenListOfListsStream(melody);
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
        } else if (countLeap > 1 && Math.abs(melodyArray.get(0) - melodyArray.get(melodyArray.size() - 1)) > 0) {
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
        this.fitness2 = fitness;
    }

    public double getFitness2() {
        return fitness2;
    }

    public void setFrontRank(int frontRank) {
        this.frontRank = frontRank;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

    public boolean dominates(Individual q) {
        if (fitness1 < q.fitness1 && fitness2 <= q.fitness2) {
            return true;
        }
        return fitness1 <= q.fitness1 && fitness2 < q.fitness2;

//        if (fitness1 < q.fitness1 && fitness2 <= q.fitness2){
//            return true;
//        } if (fitness1 <= q.fitness1 && fitness2 < q.fitness2){
//            return true;
//        }
//        return false;
    }

    public int getFrontRank() {
        return frontRank;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }
}
