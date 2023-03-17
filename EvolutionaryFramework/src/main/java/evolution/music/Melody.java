package evolution.music;

import com.google.common.collect.ImmutableList;
import evolution.helper.Helper;

import java.util.ArrayList;

public class Melody {
    private ArrayList<ArrayList<Integer>> melody;
    private String melodyJFugue;

    public Melody() {
    }

    public void setMelody(ArrayList<ArrayList<Integer>> melody) {
        this.melody = melody;
    }

    public void setMelodyJFugue(int maxNumberOfNotes) {
        StringBuilder pattern = new StringBuilder();
        int count = 1;
        double durationValue;
        ArrayList<Integer> melodyArray = Helper.flattenListOfListsStream(melody);
        for (int i = 0; i < melodyArray.size(); i++) {
            if (i == 0) {
                pattern.append(melodyArray.get(i));
            } else {
                if (melodyArray.get(i) == -1) {
                    durationValue = (double) count / maxNumberOfNotes;
                    pattern.append("/").append(durationValue).append(" ");
                    count = 1;
                    pattern.append("R");
                } else if (melodyArray.get(i) != 0) {
                    durationValue = (double) count / maxNumberOfNotes;
                    pattern.append("/").append(durationValue).append(" ");
                    count = 1;
                    pattern.append(melodyArray.get(i));
                    if (i == melodyArray.size() - 1) {
                        durationValue = (double) count / maxNumberOfNotes;
                        pattern.append("/").append(durationValue).append(" ");
                    }
                } else {
                    count += 1;
                }
            }
        }
        this.melodyJFugue = pattern.toString();
    }

    public void initializeMelody(ImmutableList<Integer> representation, String representationType, int numberOfBars, int maxNumberOfNotes) {

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

    public String getMelodyJFugue() {
        return melodyJFugue;
    }

    public ArrayList<ArrayList<Integer>> getMelody() {
        return melody;
    }

}
