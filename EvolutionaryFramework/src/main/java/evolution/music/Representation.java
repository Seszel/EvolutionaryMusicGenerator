package evolution.music;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
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

}
