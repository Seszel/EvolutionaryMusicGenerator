package evolution.music;

import com.google.common.collect.ImmutableList;
import evolution.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Genome {
    private List<List<Integer>> melody;
    private String melodyJFugue;

    public Genome() {
    }

    public void setMelody(List<List<Integer>> melody) {
        this.melody = melody;
    }

    public void setMelodyJFugue(int maxNumberOfNotes) {
        StringBuilder pattern = new StringBuilder();
        int count = 1;
        double durationValue;
        List<Integer> melodyArray = Util.flattenListOfListsStream(melody);
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

        List<List<Integer>> melody = new ArrayList<>();
        int representationSize = representation.size();

        if ("f1".equals(representationType)) {
            int notesLeftForBar;
            int numberOfNotes;
            for (int i = 0; i < numberOfBars; i++) {
                notesLeftForBar = maxNumberOfNotes;
                List<Integer> tempBar = new ArrayList<>();
                while (notesLeftForBar > 0) {
                    tempBar.add(representation.get(Util.getRandomNumber(0, representationSize)));
                    notesLeftForBar -= 1;
                    numberOfNotes = Util.getRandomNumber(0, notesLeftForBar);
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

    public List<List<Integer>> getMelody() {
        return melody;
    }

}
