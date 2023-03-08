package evolution.music;

import evolution.helper.Helper;

import java.util.ArrayList;
import java.util.HashMap;

public class Melody {
    private final int numberOfBars;
    private final String representationType;
    private ArrayList<ArrayList<String>> melody;

    public Melody(int numberOfBars, String representationType) {
        this.numberOfBars = numberOfBars;
        this.representationType = representationType;
    }

    public ArrayList<ArrayList<String>> getMelody() {
        return melody;
    }

    public void changeMelody(){
        System.out.println("Changed melody!");
    }
    public void initializeMelody() {
        ArrayList<ArrayList<String>> melody = new ArrayList<>();
        switch (representationType){
            case "Pitch midi numbers and duration letters":

                HashMap<String, Double> duration = new HashMap<>();
                duration.put("w", 1.0);
                duration.put("h", 0.5);
                duration.put("q", 0.25);
                duration.put("e", 0.125);
                duration.put("s", 0.0625);

                HashMap<Integer, String> numbersOfDurationKeys = new HashMap<>();
                numbersOfDurationKeys.put(0, "w");
                numbersOfDurationKeys.put(1, "h");
                numbersOfDurationKeys.put(2, "q");
                numbersOfDurationKeys.put(3, "e");
                numbersOfDurationKeys.put(4, "s");


                Double barDuration = 1.0;
                Double noteDurationValue;
                String noteDurationKey;
                for (int i=0; i<numberOfBars; i++){
                    ArrayList<String> tempBar = new ArrayList<>();
                    tempBar.add(String.valueOf(Helper.getRandomNumber(48,84)));
                    boolean chosen = false;
                    do {
                        noteDurationKey = numbersOfDurationKeys.get(Helper.getRandomNumber(0, 4));
                        noteDurationValue = duration.get(noteDurationKey);
                        if (barDuration - noteDurationValue >= 0){
                            tempBar.add(noteDurationKey);
                            chosen = true;
                        }
                    } while (!chosen);

                    melody.add(tempBar);
                }
                break;
        }

        this.melody = melody;
    }
}
