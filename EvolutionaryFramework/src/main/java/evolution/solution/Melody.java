package evolution.solution;

import java.util.ArrayList;

public class Melody {
    private int id;
    private String representationType;
    private ArrayList<Note> notesOfTheMelody;
    private MusicRepresentation<Integer> melodyRepresentation; // Tu jednak trzeba to jakoś inaczej rozwiązać, a nie harcodeowac, może podklasy melodii albo melodia i rozwiązanie
    private int fitness1;
    private int fitness2;

    public ArrayList<Note> melodyRepr2Notes(){
        System.out.println("Zamienione!");
        return new ArrayList<>();
    }

}
