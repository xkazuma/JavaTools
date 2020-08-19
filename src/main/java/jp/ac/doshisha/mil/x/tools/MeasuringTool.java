package jp.ac.doshisha.mil.x.tools;

import java.util.HashMap;
import java.util.Map;

public class MeasuringTool {
    private static Map<String, Long> startTimes  = null;
    private static Map<String, Long> endTimes    = null;
    private static Map<String, Long> startRAMs   = null;
    private static Map<String, Long> endRAMs     = null;

    public static void measureStart(String key) {
        Runtime.getRuntime().gc();

        Long startTime = System.nanoTime();
        Long startRAM  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        if(startTimes == null) {
            startTimes = new HashMap<>();
            startRAMs  = new HashMap<>();
            endTimes = new HashMap<>();
            endRAMs  = new HashMap<>();
            startTimes.put(key, startTime);
            startRAMs.put(key,  startRAM);
        } else {
            if (startTimes.containsKey(key)) {
                System.err.println("Already started measuring.");
                System.exit(-1);
            }
            startTimes.put(key, startTime);
            startRAMs.put(key,  startRAM);
        }
    }

    public static void measureEnd(String key) {
        Long endTime = System.nanoTime();
        Long endRAM  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        if(startTimes == null) {
            System.err.println("Not yet started: " + key);
            System.exit(-1);
        } else {
            if (startTimes.containsKey(key)) {
                endTimes.put(key, endTime);
                endRAMs.put(key,  endRAM);
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
        result.put("RAM",  endRAMs.get(key)  - startRAMs.get(key));

        return result;
    }

    public static void printResult(String key) {
        var extractingRPathFromHubMeasure = MeasuringTool.getResult(key);
        System.out.println(
                String.format(
                        "RESULTROW-%s\tTime-um:%d RAM-Byte:%d",
                        key, extractingRPathFromHubMeasure.get("Time"), extractingRPathFromHubMeasure.get("RAM")
                ));
    }
}
