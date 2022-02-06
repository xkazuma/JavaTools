package jp.ac.doshisha.mil.x.tools;

import java.util.HashMap;
import java.util.Map;

public class MeasuringTool {
    private static Map<String, Long> startTimes  = null;
    private static Map<String, Long> endTimes    = null;

    public static void measureStart(String key) {
        Long startTime = System.nanoTime();
        Long startRAM  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        if(startTimes == null) {
            startTimes = new HashMap<>();
            endTimes = new HashMap<>();
            startTimes.put(key, startTime);
        } else {
            if (startTimes.containsKey(key)) {
                System.err.println("Already started measuring.");
                System.exit(-1);
            }
            startTimes.put(key, startTime);
        }
    }

    public static void measureEnd(String key) {
        Long endTime = System.nanoTime();

        if(startTimes == null) {
            System.err.println("Not yet started: " + key);
            System.exit(-1);
        } else {
            if (startTimes.containsKey(key)) {
                endTimes.put(key, endTime);
            } else {
                System.err.println("Not yet started: " + key);
                System.exit(-1);
            }
        }
    }

    public static Map<String, Long> getResult(String key) {
        if(startTimes == null) {
            System.err.println("Not yet used.");
            System.exit(-1);
        }
        if(startTimes.get(key) == null) {
            System.err.println("Not yet started.");
            System.exit(-1);
        }
        if(endTimes.get(key) == null) {
            System.err.println("Not yet finished.");
            System.exit(-1);
        }
        Map<String, Long> result = new HashMap<>();
        result.put("Time", endTimes.get(key) - startTimes.get(key) );

        return result;
    }

    public static void printResult(String key) {
        var extractingRPathFromHubMeasure = MeasuringTool.getResult(key);
        System.out.printf(
                "RESULTROW-%s\tTime-um:%d",
                key, extractingRPathFromHubMeasure.get("Time")
        );
    }
}
