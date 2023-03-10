package evolution.music;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.sun.tools.javac.util.List;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;

/**
 * Class that generates representations
 */

public class Representation {

    public static ImmutableList<Integer> getRepresentationInt(String representationType) {
        ImmutableList<Integer> representationInt;
        if ("f1".equals(representationType)) {
            System.out.println("Representation f1");
            ArrayList<Integer> reprList = new ArrayList<>();
            // C4 - C7
            for (int i = 48; i < 85; i++) {
                reprList.add(i);
            }
            reprList.add(0);
            reprList.add(-1);
            representationInt = ImmutableList.<Integer>builder()
                    .addAll(reprList)
                    .build();
        } else {
            System.out.println("Other representation");
            return null;
        }
        return representationInt;
    }

    public static BiMap<String, Double> getDurationMap() {

        BiMap<String, Double> durationMap = HashBiMap.create();

        durationMap.put("w", 1.0);
        durationMap.put("h", 0.5);
        durationMap.put("q", 0.25);
        durationMap.put("e", 0.125);
        durationMap.put("s", 0.0625);
        durationMap.put("t", 0.03125);
        durationMap.put("x", 0.015625);
        durationMap.put("o", 0.0078125);

        return durationMap;
    }

    public static BiMap<String, Integer> getNotesMap() {

        BiMap<String, Integer> notesMap = HashBiMap.create();

        notesMap.put("C", 48);
        notesMap.put("C#", 49);
        notesMap.put("D", 50);
        notesMap.put("D#", 51);
        notesMap.put("E", 52);
        notesMap.put("F", 53);
        notesMap.put("F#", 54);
        notesMap.put("G", 55);
        notesMap.put("G#", 56);
        notesMap.put("A", 57);
        notesMap.put("A#", 58);
        notesMap.put("B", 59);

        return notesMap;
    }

    public static ArrayList<HashMap<String, List<Integer>>> getChordProgressionMajor() {

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

        return diatonicChords;
    }

    public static ArrayList<HashMap<String, List<Integer>>> getChordProgressionMinor() {

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

        return diatonicChords;
    }


}
