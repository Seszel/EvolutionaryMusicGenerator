package evolution.music;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RepresentationMap<T> {

    public T getRepresentation(String type){
        if (type.equals("f1")) {
            System.out.println("Representation f1");
            return getRepresentation_f1();
        }
        else {
            System.out.println("Other representation");
            return null;
        }
    }

    public T getRepresentation_f1(){

        Map<String, Integer> map = Stream.of(new Object[][] {
                { "Rest", -1 },

                { "Hold", 0 },

                { "A0", 1},
                { "a0", 2},
                { "B0", 3},
                { "C1", 4},
                { "c1", 5},
                { "D1", 6},
                { "d1", 7},
                { "E1", 8},
                { "F1", 9},
                { "f1", 10},
                { "G1", 11},
                { "g1", 12},
                { "A1", 13},
                { "a1", 14},
                { "B1", 15},

                { "C2", 16},
                { "c2", 17},
                { "D2", 18},
                { "d2", 19},
                { "E2", 20},
                { "F2", 21},
                { "f2", 22},
                { "G2", 23},
                { "g2", 24},
                { "A2", 25},
                { "a2", 26},
                { "B2", 27},

                { "C3", 28},
                { "c3", 29},
                { "D3", 30},
                { "d3", 31},
                { "E3", 32},
                { "F3", 33},
                { "f3", 34},
                { "G3", 35},
                { "g3", 36},
                { "A3", 37},
                { "a3", 38},
                { "B3", 39},

                { "C4", 40},
                { "c4", 41},
                { "D4", 42},
                { "d4", 43},
                { "E4", 44},
                { "F4", 45},
                { "f4", 46},
                { "G4", 47},
                { "g4", 48},
                { "A4", 49},
                { "a4", 50},
                { "B4", 51},

                { "C5", 52},
                { "c5", 53},
                { "D5", 54},
                { "d5", 55},
                { "E5", 56},
                { "F5", 57},
                { "f5", 58},
                { "G5", 59},
                { "g5", 60},
                { "A5", 61},
                { "a5", 62},
                { "B5", 63},

                { "C6", 64},
                { "c6", 65},
                { "D6", 66},
                { "d6", 67},
                { "E6", 68},
                { "F6", 69},
                { "f6", 70},
                { "G6", 71},
                { "g6", 72},
                { "A6", 73},
                { "a6", 74},
                { "B6", 75},

                { "C7", 76},
                { "c7", 77},
                { "D7", 78},
                { "d7", 79},
                { "E7", 80},
                { "F7", 81},
                { "f7", 82},
                { "G7", 83},
                { "g7", 84},
                { "A7", 85},
                { "a7", 86},
                { "B7", 87},

                { "C8", 88},


        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

        return (T) map;
    }
    public RepresentationMap() {}

}
