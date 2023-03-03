package evolution.solution;

import java.util.ArrayList;

public class MusicRepresentation<T> {
    private ArrayList<T> melodyArrayList;


    public ArrayList<T> getMelodyArrayList() {
        return melodyArrayList;
    }

    public void setMelodyArrayList(ArrayList<T> melodyArrayList) {
        this.melodyArrayList = melodyArrayList;
    }

    public MusicRepresentation(ArrayList<T> melodyArrayList) {
        this.setMelodyArrayList(melodyArrayList);
    }
}
