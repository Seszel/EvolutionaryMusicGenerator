package evolution.music;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                { "Rest", -1 },

                { "Hold", 0 },

                { "A0", 21},
                { "a0", 22},
                { "B0", 23},
                { "C1", 24},
                { "c1", 25},
                { "D1", 26},
                { "d1", 27},
                { "E1", 28},
                { "F1", 29},
                { "f1", 30},
                { "G1", 31},
                { "g1", 32},
                { "A1", 33},
                { "a1", 34},
                { "B1", 35},

                { "C2", 36},
                { "c2", 37},
                { "D2", 38},
                { "d2", 39},
                { "E2", 40},
                { "F2", 41},
                { "f2", 42},
                { "G2", 43},
                { "g2", 44},
                { "A2", 45},
                { "a2", 46},
                { "B2", 47},

                { "C3", 48},
                { "c3", 49},
                { "D3", 50},
                { "d3", 51},
                { "E3", 52},
                { "F3", 53},
                { "f3", 54},
                { "G3", 55},
                { "g3", 56},
                { "A3", 57},
                { "a3", 58},
                { "B3", 59},

                { "C4", 60},
                { "c4", 61},
                { "D4", 62},
                { "d4", 63},
                { "E4", 64},
                { "F4", 65},
                { "f4", 66},
                { "G4", 67},
                { "g4", 68},
                { "A4", 69},
                { "a4", 70},
                { "B4", 71},

                { "C5", 72},
                { "c5", 73},
                { "D5", 74},
                { "d5", 75},
                { "E5", 76},
                { "F5", 77},
                { "f5", 78},
                { "G5", 79},
                { "g5", 80},
                { "A5", 81},
                { "a5", 82},
                { "B5", 83},

                { "C6", 84},
                { "c6", 85},
                { "D6", 86},
                { "d6", 87},
                { "E6", 88},
                { "F6", 89},
                { "f6", 90},
                { "G6", 91},
                { "g6", 92},
                { "A6", 93},
                { "a6", 94},
                { "B6", 95},

                { "C7", 96},
                { "c7", 97},
                { "D7", 98},
                { "d7", 99},
                { "E7", 100},
                { "F7", 101},
                { "f7", 102},
                { "G7", 103},
                { "g7", 104},
                { "A7", 105},
                { "a7", 106},
                { "B7", 107},

                { "C8", 108},


        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
    }
    public RepresentationMap() {}

}
