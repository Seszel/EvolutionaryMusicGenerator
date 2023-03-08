package evolution.music;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class that hold map key value
 */

public class RepresentationMap {

    public Map<String, Integer> getRepresentation(String type){
        if (type.equals("f1")) {
            System.out.println("Representation f1");
            return getRepresentation_f1();
        }
        else {
            System.out.println("Other representation");
            return null;
        }
    }

    public Map<String, Integer> getRepresentation_f1(){

        return Stream.of(new Object[][] {

                { "C0", 0},
                { "C#0", 1},
                { "D0", 2},
                { "Eb0", 3},
                { "E0", 4},
                { "F0", 5},
                { "F#0", 6},
                { "G0", 7},
                { "G#0", 8},
                { "A0", 9},
                { "Bb0", 10},
                { "B0", 11},

                { "C1", 12},
                { "C#1", 13},
                { "D1", 14},
                { "Eb1", 15},
                { "E1", 16},
                { "F1", 17},
                { "F#1", 18},
                { "G1", 19},
                { "G#1", 20},
                { "A1", 21},
                { "Bb1", 22},
                { "B1", 23},

                { "C2", 24},
                { "C#2", 25},
                { "D2", 26},
                { "Eb2", 27},
                { "E2", 28},
                { "F2", 29},
                { "F#2", 30},
                { "G2", 31},
                { "G#2", 32},
                { "A2", 33},
                { "Bb2", 34},
                { "B2", 35},

                { "C3", 36},
                { "C#3", 37},
                { "D3", 38},
                { "Eb3", 39},
                { "E3", 40},
                { "F3", 41},
                { "F#3", 42},
                { "G3", 43},
                { "G#3", 44},
                { "A3", 45},
                { "Bb3", 46},
                { "B3", 47},

                { "C4", 48},
                { "C#4", 49},
                { "D4", 50},
                { "Eb4", 51},
                { "E4", 52},
                { "F4", 53},
                { "F#4", 54},
                { "G4", 55},
                { "G#4", 56},
                { "A4", 57},
                { "Bb4", 58},
                { "B4", 59},

                { "C5", 60},
                { "C#5", 61},
                { "D5", 62},
                { "Eb5", 63},
                { "E5", 64},
                { "F5", 65},
                { "F#5", 66},
                { "G5", 67},
                { "G#5", 68},
                { "A5", 69},
                { "Bb5", 70},
                { "B5", 71},

                { "C6", 72},
                { "C#6", 73},
                { "D6", 74},
                { "Eb6", 75},
                { "E6", 76},
                { "F6", 77},
                { "F#6", 78},
                { "G6", 79},
                { "G#6", 80},
                { "A6", 81},
                { "Bb6", 82},
                { "B6", 83},

                { "C7", 84},
                { "C#7", 85},
                { "D7", 86},
                { "Eb7", 87},
                { "E7", 88},
                { "F7", 89},
                { "F#7", 90},
                { "G7", 91},
                { "G#7", 92},
                { "A7", 93},
                { "Bb7", 94},
                { "B7", 95},

                { "C8", 96},
                { "C#8", 97},
                { "D8", 98},
                { "Eb8", 99},
                { "E8", 100},
                { "F8", 101},
                { "F#8", 102},
                { "G8", 103},
                { "G#8", 104},
                { "A8", 105},
                { "Bb8", 106},
                { "B8", 107},

                { "C9", 108},
                { "C#9", 109},
                { "D9", 110},
                { "Eb9", 111},
                { "E9", 112},
                { "F9", 113},
                { "F#9", 114},
                { "G9", 115},
                { "G#9", 116},
                { "A9", 117},
                { "Bb9", 118},
                { "B9", 119},

                { "C10", 120},
                { "C#9", 121},
                { "D10", 122},
                { "Eb10", 123},
                { "E10", 124},
                { "F10", 125},
                { "F#10", 126},
                { "G10", 127},


        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
    }
    public RepresentationMap() {}

}
