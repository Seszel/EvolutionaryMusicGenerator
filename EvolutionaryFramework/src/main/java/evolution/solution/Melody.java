package evolution.solution;

import java.util.ArrayList;

public class Melody<T> {
    private final int id;
    private final String representationType;
    private ArrayList<Note> notesOfTheMelody;
    private ArrayList<T> melodyRepresentation;
    private int fitness1;
    private int fitness2;

    public Melody(int id, String representationType, ArrayList<Note> notesOfTheMelody, ArrayList<T> melodyRepresentation, int fitness1, int fitness2) {
        this.id = id;
        this.representationType = representationType;
        this.notesOfTheMelody = notesOfTheMelody;
        this.melodyRepresentation = melodyRepresentation;
        this.fitness1 = fitness1;
        this.fitness2 = fitness2;
    }


    public ArrayList<Note> melodyRepr2Notes() {
        return null;
    }


    public int getId() {
        return id;
    }

    public String getRepresentationType() {
        return representationType;
    }

    public ArrayList<Note> getNotesOfTheMelody() {
        return notesOfTheMelody;
    }

    public void setNotesOfTheMelody(ArrayList<Note> notesOfTheMelody) {
        this.notesOfTheMelody = notesOfTheMelody;
    }

    public ArrayList<T> getMelodyRepresentation() {
        return melodyRepresentation;
    }

    public void setMelodyRepresentation(ArrayList<T> melodyRepresentation) {
        this.melodyRepresentation = melodyRepresentation;
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
