package evolution.util;

import evolution.solution.Individual;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Util {



    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static double getEuclideanDistance(List<Double> element1, List<Double> element2) {
        double distance = 0;
        for (int i = 0; i < element1.size(); i++) {
            distance += Math.pow((element1.get(i) - element2.get(i)), 2);
        }
        return distance;
    }

    public static <T> List<T> flattenListOfListsStream(List<List<T>> list) {
        return list.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }



}
