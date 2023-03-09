package evolution.music;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that generates representations
 */

public class Representation {

    public static ImmutableList<Integer> getRepresentationInt(String representationType){
        ImmutableList<Integer> representationInt;
        if ("f1".equals(representationType)) {
            System.out.println("Representation f1");
            List<Integer> reprList = new ArrayList<>();
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
    public static BiMap<String, Double> getDurationMap(){
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

}
