package evolution.helper;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Helper {
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static <T> ArrayList<T> flattenListOfListsStream(ArrayList<ArrayList<T>> list) {
        return (ArrayList<T>) list.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
