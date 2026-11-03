package collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CollectionPerformanceExperiment {

    private static final int N = 10_000;
    private static final int WARMUP_RUNS = 2;
    private static final int MEASURE_RUNS = 5;

    private static final String[] NAMES = {
            "add to end",
            "add to start",
            "insert in middle",
            "get by index",
            "remove from start",
            "remove from end"
    };

    private static long checksum = 0;

    public static void main(String[] args) {
        System.out.printf("%-20s %10s %15s %15s%n", "operation", "elements", "ArrayList, ms", "LinkedList, ms");
        for (int scenario = 0; scenario < NAMES.length; scenario++) {
            double arrayTime = measure(scenario, false) / 1_000_000.0;
            double linkedTime = measure(scenario, true) / 1_000_000.0;
            System.out.printf("%-20s %10d %15.3f %15.3f%n", NAMES[scenario], N, arrayTime, linkedTime);
        }
        System.out.println("checksum: " + checksum);
    }

    private static long measure(int scenario, boolean linked) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            List<Integer> list = prepare(scenario, linked);
            checksum += run(scenario, list);
        }
        long[] times = new long[MEASURE_RUNS];
        for (int i = 0; i < MEASURE_RUNS; i++) {
            List<Integer> list = prepare(scenario, linked);
            long start = System.nanoTime();
            long result = run(scenario, list);
            long end = System.nanoTime();
            checksum += result;
            times[i] = end - start;
        }
        Arrays.sort(times);
        return times[MEASURE_RUNS / 2];
    }

    private static List<Integer> prepare(int scenario, boolean linked) {
        List<Integer> list;
        if (linked) {
            list = new LinkedList<>();
        } else {
            list = new ArrayList<>();
        }
        if (scenario >= 3) {
            for (int i = 0; i < N; i++) {
                list.add(i);
            }
        }
        return list;
    }

    private static long run(int scenario, List<Integer> list) {
        long sum = 0;
        if (scenario == 0) {
            for (int i = 0; i < N; i++) {
                list.add(i);
            }
            sum = list.size() + list.get(list.size() - 1);
        } else if (scenario == 1) {
            for (int i = 0; i < N; i++) {
                list.add(0, i);
            }
            sum = list.size() + list.get(0);
        } else if (scenario == 2) {
            for (int i = 0; i < N; i++) {
                list.add(list.size() / 2, i);
            }
            sum = list.size() + list.get(list.size() / 2);
        } else if (scenario == 3) {
            for (int i = 0; i < N; i++) {
                sum += list.get(i);
            }
        } else if (scenario == 4) {
            for (int i = 0; i < N; i++) {
                sum += list.remove(0);
            }
        } else {
            for (int i = 0; i < N; i++) {
                sum += list.remove(list.size() - 1);
            }
        }
        return sum;
    }
}
