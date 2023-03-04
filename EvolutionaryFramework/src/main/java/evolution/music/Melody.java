package evolution.music;

import java.util.ArrayList;

public class Melody<T> {
    private final String representationType;
    private ArrayList<T> melodyRepresentation;
    private ArrayList<Note> notesOfTheMelody;

    public Melody(String representationType) {
        this.representationType = representationType;
    }

    // 28-63, 0, -1

    public String getRepresentationType() {
        return representationType;
    }



}
