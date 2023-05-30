package evolution.music;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Representation {

    public static ImmutableList<Integer> getReprInt(String reprType) {
        ImmutableList<Integer> reprInt;
        if ("f1".equals(reprType)) {
//            System.out.println("Representation f1");
            ArrayList<Integer> reprList = new ArrayList<>();
            // C4 - C7
//            for (int i = 48; i < 85; i++) {
//                reprList.add(i);
//            }
            for (int i = 50; i < 75; i++) {
                reprList.add(i);
            }
//            reprList.add(0);
//            reprList.add(-1);
            reprInt = ImmutableList.<Integer>builder()
                    .addAll(reprList)
                    .build();
        } else {
            System.out.println("Other representation");
            return null;
        }
        return reprInt;
    }

    public static final ImmutableBiMap<String, Double> DurationMap =
            new ImmutableBiMap.Builder<String, Double>()
                    .put("w", 1.0)
                    .put("h", 0.5)
                    .put("q", 0.25)
                    .put("e", 0.125)
                    .put("s", 0.0625)
                    .put("t", 0.03125)
                    .put("o", 0.0078125)
                    .put("x", 0.015625)
                    .build();

    public static final ImmutableBiMap<String, Integer> NotesMap =
            new ImmutableBiMap.Builder<String, Integer>()
                    .put("C", 48)
                    .put("C#", 49)
                    .put("D", 50)
                    .put("D#", 51)
                    .put("E", 52)
                    .put("F", 53)
                    .put("F#", 54)
                    .put("G", 55)
                    .put("G#", 56)
                    .put("A", 57)
                    .put("A#", 58)
                    .put("B", 59)
                    .build();

    public static ArrayList<HashMap<String, List<Integer>>> ChordProgression(String keyType) {
        switch (keyType) {
            case "MAJOR":
                return Representation.ChordProgressionMajor;
            case "MINOR":
                return Representation.ChordProgressionMinor;
            default:
                return null;
        }
    }

    public static final ArrayList<HashMap<String, List<Integer>>> ChordProgressionMajor;
    static {
        ArrayList<HashMap<String, List<Integer>>> diatonicChords = new ArrayList<>();

        HashMap<String, List<Integer>> diatonicChordsChordTone = new HashMap<>();
        diatonicChordsChordTone.put("I", List.of(0, 4, 7));
        diatonicChordsChordTone.put("ii", List.of(2, 5, 9));
        diatonicChordsChordTone.put("iii", List.of(4, 7, 11));
        diatonicChordsChordTone.put("IV", List.of(5, 9, 0));
        diatonicChordsChordTone.put("V", List.of(7, 11, 2));
        diatonicChordsChordTone.put("vi", List.of(9, 0, 4));
        diatonicChordsChordTone.put("vii-", List.of(11, 2, 5));

        diatonicChords.add(diatonicChordsChordTone);

        HashMap<String, List<Integer>> diatonicChordsTensionNote = new HashMap<>();
        diatonicChordsTensionNote.put("I", List.of(2, 9, 11));
        diatonicChordsTensionNote.put("ii", List.of(4, 7, 0));
        diatonicChordsTensionNote.put("iii", List.of(9, 2));
        diatonicChordsTensionNote.put("IV", List.of(7, 2, 4));
        diatonicChordsTensionNote.put("V", List.of(9, 4, 5));
        diatonicChordsTensionNote.put("vi", List.of(2, 7, 11));
        diatonicChordsTensionNote.put("vii-", List.of(7, 9));

        diatonicChords.add(diatonicChordsTensionNote);


        HashMap<String, List<Integer>> diatonicChordsAvoidNote = new HashMap<>();
        diatonicChordsAvoidNote.put("I", List.of(5));
        diatonicChordsAvoidNote.put("ii", List.of(11));
        diatonicChordsAvoidNote.put("iii", List.of(5, 0));
        diatonicChordsAvoidNote.put("IV", List.of(11));
        diatonicChordsAvoidNote.put("V", List.of(0));
        diatonicChordsAvoidNote.put("vi", List.of(5));
        diatonicChordsAvoidNote.put("vii-", List.of(0, 2));

        diatonicChords.add(diatonicChordsAvoidNote);

        ChordProgressionMajor = diatonicChords;
    }

    public static ArrayList<HashMap<String, List<Integer>>> ChordProgressionMinor;

    static {

        ArrayList<HashMap<String, List<Integer>>> diatonicChords = new ArrayList<>();

        HashMap<String, List<Integer>> diatonicChordsChordTone = new HashMap<>();

        // natural minor
        diatonicChordsChordTone.put("i", List.of(0, 3, 7));
        diatonicChordsChordTone.put("ii-", List.of(2, 5, 8));
        diatonicChordsChordTone.put("III", List.of(3, 7, 10));
        diatonicChordsChordTone.put("iv", List.of(5, 8, 0));
        diatonicChordsChordTone.put("v", List.of(7, 10, 2));
        diatonicChordsChordTone.put("VI", List.of(8, 0, 3));
        diatonicChordsChordTone.put("VII", List.of(10, 2, 5));

        // harmonic minor
        diatonicChordsChordTone.put("III+", List.of(3, 7, 11));
        diatonicChordsChordTone.put("V", List.of(7, 11, 2));
        diatonicChordsChordTone.put("vii-", List.of(11, 2, 5));

        // melodic minor
        diatonicChordsChordTone.put("ii", List.of(2, 5, 9));
        diatonicChordsChordTone.put("IV", List.of(5, 9, 0));
        diatonicChordsChordTone.put("vi-", List.of(9, 0, 3));

        diatonicChords.add(diatonicChordsChordTone);

        HashMap<String, List<Integer>> diatonicChordsTensionNote = new HashMap<>();
        HashMap<String, List<Integer>> diatonicChordsAvoidNote = new HashMap<>();

        ChordProgressionMinor = diatonicChords;
    }

}
