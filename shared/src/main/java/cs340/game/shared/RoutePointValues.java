package cs340.game.shared;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stephen on 11/11/2018.
 */

public class RoutePointValues {
    private Map<Integer, Integer> pointValues;
    private static RoutePointValues instance;

    public static RoutePointValues getInstance() {
        if(instance == null) {
            instance = new RoutePointValues();
        }
        return instance;
    }

    private RoutePointValues() {
        pointValues = new HashMap<>();
        pointValues.put(1, 1);
        pointValues.put(2, 2);
        pointValues.put(3, 4);
        pointValues.put(4, 7);
        pointValues.put(5, 10);
        pointValues.put(6, 15);
    }

    public int getPointValue(int routeLength) {
        return pointValues.get(routeLength);
    }
}
