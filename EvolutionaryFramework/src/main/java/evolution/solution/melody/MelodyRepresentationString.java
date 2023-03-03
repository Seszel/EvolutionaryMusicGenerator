package evolution.solution.melody;

import evolution.solution.MusicRepresentation;
import evolution.solution.Note;

import java.util.ArrayList;

public class MelodyRepresentationString extends AMelody {

    private MusicRepresentation<String> melodyRepresentation;

    public MelodyRepresentationString(int id, String representationType, ArrayList<Note> notesOfTheMelody, int fitness1, int fitness2) {
        super(id, representationType, notesOfTheMelody, fitness1, fitness2);
    }

    @Override
    public ArrayList<Note> melodyRepr2Notes(){
        System.out.println("Changed String to Notes!");
        return new ArrayList<>();
    }

    public MusicRepresentation<String> getMelodyRepresentation() {
        return melodyRepresentation;
    }

    public void setMelodyRepresentation(MusicRepresentation<String> melodyRepresentation) {
        this.melodyRepresentation = melodyRepresentation;
    }
}
