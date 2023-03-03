package evolution.solution.melody;

import evolution.solution.MusicRepresentation;
import evolution.solution.Note;

import java.util.ArrayList;

public class MelodyRepresentationInt extends AMelody {

    private MusicRepresentation<Integer> melodyRepresentation;

    public MelodyRepresentationInt(int id, String representationType, ArrayList<Note> notesOfTheMelody, int fitness1, int fitness2) {
        super(id, representationType, notesOfTheMelody, fitness1, fitness2);
    }

    @Override
    public ArrayList<Note> melodyRepr2Notes(){
        System.out.println("Changed Int to Notes!");
        return new ArrayList<>();
    }

    public MusicRepresentation<Integer> getMelodyRepresentation() {
        return melodyRepresentation;
    }

    public void setMelodyRepresentation(MusicRepresentation<Integer> melodyRepresentation) {
        this.melodyRepresentation = melodyRepresentation;
    }
}
