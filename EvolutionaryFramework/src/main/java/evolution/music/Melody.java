package evolution.music;

import com.google.common.collect.ImmutableList;
import evolution.helper.Helper;

import java.util.ArrayList;

public class Melody {
    private final int numberOfBars;
    private final int maxNumberOfNotes;
    private final String representationType;
    private ArrayList<ArrayList<Integer>> melody;

    public Melody(int numberOfBars, int maxNumberOfNotes, String representationType) {
        this.numberOfBars = numberOfBars;
        this.maxNumberOfNotes = maxNumberOfNotes;
        this.representationType = representationType;
    }

    public ArrayList<ArrayList<Integer>> getMelody() {
        return melody;
    }

    public void changeMelody() {
        System.out.println("Changed melody!");
    }

    public void initializeMelody(ImmutableList<Integer> representation) {
        ArrayList<ArrayList<Integer>> melody = new ArrayList<>();
        int representationSize = representation.size();
        if ("f1".equals(representationType)) {
            int notesLeftForBar;
            int numberOfNotes;
            for (int i = 0; i < numberOfBars; i++) {
                notesLeftForBar = maxNumberOfNotes;
                ArrayList<Integer> tempBar = new ArrayList<>();
                while (notesLeftForBar > 0) {
                    tempBar.add(representation.get(Helper.getRandomNumber(0, representationSize)));
                    notesLeftForBar -= 1;
                    numberOfNotes = Helper.getRandomNumber(0, notesLeftForBar);
                    if (numberOfNotes != 0) {
                        for (int k = 1; k < numberOfNotes; k++) {
                            tempBar.add(0);
                            notesLeftForBar -= 1;
                        }
                    }
                }
                melody.add(tempBar);
            }
        } else {
            System.out.println("No melody without representation type");
        }
        this.melody = melody;
    }
}
