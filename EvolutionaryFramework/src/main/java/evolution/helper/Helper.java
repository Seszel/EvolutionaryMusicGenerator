package evolution.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Helper {
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
