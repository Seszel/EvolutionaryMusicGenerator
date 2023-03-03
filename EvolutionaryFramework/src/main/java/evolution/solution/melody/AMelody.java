package evolution.solution.melody;

import evolution.solution.Note;

import java.util.ArrayList;

abstract class AMelody {
    private final int id;
    private String representationType;
    private ArrayList<Note> notesOfTheMelody;
    private int fitness1;
    private int fitness2;

    public AMelody(int id, String representationType, ArrayList<Note> notesOfTheMelody, int fitness1, int fitness2) {
        this.id = id;
        this.representationType = representationType;
        this.notesOfTheMelody = notesOfTheMelody;
        this.fitness1 = fitness1;
        this.fitness2 = fitness2;
    }

    public abstract ArrayList<Note> melodyRepr2Notes();

    public int getId() {
        return id;
    }

    public String getRepresentationType() {
        return representationType;
    }

    public void setRepresentationType(String representationType) {
        this.representationType = representationType;
    }

    public ArrayList<Note> getNotesOfTheMelody() {
        return notesOfTheMelody;
    }

    public void setNotesOfTheMelody(ArrayList<Note> notesOfTheMelody) {
        this.notesOfTheMelody = notesOfTheMelody;
    }

    public int getFitness1() {
        return fitness1;
    }

    public void setFitness1(int fitness1) {
        this.fitness1 = fitness1;
    }

    public int getFitness2() {
        return fitness2;
    }

    public void setFitness2(int fitness2) {
        this.fitness2 = fitness2;
    }
}
