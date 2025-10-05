package cli;

import algorithms.InsertionSort;
import metrics.PerformanceTracker;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Command Line Interface for benchmarking Insertion Sort performance
 * Supports multiple input sizes and distributions for comprehensive testing
 */
public class BenchmarkRunner {

    private static final int[] DEFAULT_SIZES = {100, 1000, 10000, 50000};
    private static final String[] DISTRIBUTIONS = {"random", "sorted", "reverse", "nearly_sorted"};

    public static void main(String[] args) {
        if (args.length == 0) {
            runComprehensiveBenchmarks();
        } else {
            runCustomBenchmark(args);
        }
    }

    /**
     * Run comprehensive benchmarks across all sizes and distributions
     */
    private static void runComprehensiveBenchmarks() {
        System.out.println("Running comprehensive insertion sort benchmarks...");
        System.out.println("Input Size,Data Distribution,Time (ns),Comparisons,Swaps,Array Accesses,Memory Allocations");

        for (int size : DEFAULT_SIZES) {
            for (String distribution : DISTRIBUTIONS) {
                if (size <= 100000) { // Memory safety check
                    runSingleBenchmark(size, distribution);
                }
            }
        }
    }

    /**
     * Run benchmark with custom parameters
     */
    private static void runCustomBenchmark(String[] args) {
        try {
            int size = Integer.parseInt(args[0]);
            String distribution = args.length > 1 ? args[1] : "random";

            if (size <= 0) {
                System.err.println("Error: Size must be positive");
                printUsage();
                return;
            }

            if (size > 1000000) {
                System.err.println("Warning: Large sizes may cause memory issues");
            }

            System.out.println("Input Size,Data Distribution,Time (ns),Comparisons,Swaps,Array Accesses,Memory Allocations");
            runSingleBenchmark(size, distribution);

        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid size parameter");
            printUsage();
        }
    }

    /**
     * Execute single benchmark run
     */
    private static void runSingleBenchmark(int size, String distribution) {
        InsertionSort sorter = new InsertionSort();
        int[] data = generateTestData(size, distribution);

        // Warm-up phase (5 iterations)
        for (int i = 0; i < 5; i++) {
            sorter.sort(Arrays.copyOf(data, data.length));
        }

        // Actual measurement
        long startTime = System.nanoTime();
        int[] result = sorter.sort(data);
        long endTime = System.nanoTime();

        PerformanceTracker tracker = sorter.getPerformanceTracker();
        long timeNs = endTime - startTime; // НАНОСЕКУНДЫ вместо миллисекунд

        // Verify correctness
        if (!isSorted(result)) {
            System.err.println("ERROR: Sorting verification failed for size " + size + " distribution " + distribution);
            return;
        }

        // Output results in CSV format
        System.out.printf("%d,%s,%d,%d,%d,%d,%d\n",
                size, distribution, timeNs, // НАНОСЕКУНДЫ
                tracker.getComparisons(), tracker.getSwaps(),
                tracker.getArrayAccesses(), tracker.getMemoryAllocations());
    }

    /**
     * Generate test data with specified distribution
     */
    private static int[] generateTestData(int size, String distribution) {
        Random random = new Random(42); // Fixed seed for reproducibility
        int[] data = new int[size];

        switch (distribution.toLowerCase()) {
            case "sorted":
                // Best case: already sorted
                for (int i = 0; i < size; i++) {
                    data[i] = i;
                }
                break;

            case "reverse":
                // Worst case: reverse sorted
                for (int i = 0; i < size; i++) {
                    data[i] = size - i;
                }
                break;

            case "nearly_sorted":
                // Nearly sorted with some inversions
                for (int i = 0; i < size; i++) {
                    data[i] = i;
                }
                // Introduce ~10% random swaps
                for (int i = 0; i < size / 10; i++) {
                    int idx1 = random.nextInt(size);
                    int idx2 = random.nextInt(size);
                    int temp = data[idx1];
                    data[idx1] = data[idx2];
                    data[idx2] = temp;
                }
                break;

            case "random":
            default:
                // Average case: random data
                for (int i = 0; i < size; i++) {
                    data[i] = random.nextInt(size * 10);
                }
                break;
        }

        return data;
    }

    /**
     * Verify that array is sorted in non-decreasing order
     */
    private static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Print usage instructions
     */
    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  No arguments: Run comprehensive benchmarks");
        System.out.println("  With arguments: java BenchmarkRunner <size> [distribution]");
        System.out.println();
        System.out.println("Distributions: random, sorted, reverse, nearly_sorted");
        System.out.println("Examples:");
        System.out.println("  java BenchmarkRunner 1000 random");
        System.out.println("  java BenchmarkRunner 5000 sorted");
        System.out.println("  java BenchmarkRunner 10000 reverse");
    }
}