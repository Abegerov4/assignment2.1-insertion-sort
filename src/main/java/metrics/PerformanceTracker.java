package metrics;

import java.util.HashMap;
import java.util.Map;

/**
 * Performance metrics tracker for algorithm analysis
 * Tracks comparisons, swaps, array accesses, and execution time
 */
public class PerformanceTracker {
    private long startTime;
    private long endTime;
    private final Map<String, Long> metrics;

    public PerformanceTracker() {
        this.metrics = new HashMap<>();
        reset();
    }

    /**
     * Reset all metrics to zero
     */
    public void reset() {
        metrics.put("comparisons", 0L);
        metrics.put("swaps", 0L);
        metrics.put("arrayAccesses", 0L);
        metrics.put("memoryAllocations", 0L);
        startTime = 0;
        endTime = 0;
    }

    public void startTimer() {

        startTime = System.nanoTime();
    }

    public void stopTimer() {

        endTime = System.nanoTime();
    }

    public void recordComparison() {

        metrics.put("comparisons", metrics.get("comparisons") + 1);
    }

    public void recordComparison(int count) {
        metrics.put("comparisons", metrics.get("comparisons") + count);
    }

    public void recordSwap() {
        metrics.put("swaps", metrics.get("swaps") + 1);
    }

    public void recordSwap(int count) {
        metrics.put("swaps", metrics.get("swaps") + count);
    }

    public void recordArrayAccess() {
        metrics.put("arrayAccesses", metrics.get("arrayAccesses") + 1);
    }

    public void recordArrayAccess(int count) {
        metrics.put("arrayAccesses", metrics.get("arrayAccesses") + count);
    }

    public void recordMemoryAllocation(long bytes) {
        metrics.put("memoryAllocations", metrics.get("memoryAllocations") + bytes);
    }

    // Getters
    public long getElapsedTime() {
        return endTime - startTime;
    }

    public long getComparisons() {
        return metrics.get("comparisons");
    }

    public long getSwaps() {
        return metrics.get("swaps");
    }

    public long getArrayAccesses() {
        return metrics.get("arrayAccesses");
    }

    public long getMemoryAllocations() {
        return metrics.get("memoryAllocations");
    }

    /**
     * Export metrics as CSV string
     */
    public String toCSV() {
        return String.format("%d,%d,%d,%d,%d",
                getElapsedTime(), getComparisons(), getSwaps(),
                getArrayAccesses(), getMemoryAllocations());
    }

    /**
     * Print formatted metrics to console
     */
    public void printMetrics() {
        System.out.println("=== Performance Metrics ===");
        System.out.printf("Execution Time: %d ns\n", getElapsedTime());
        System.out.printf("Comparisons: %d\n", getComparisons());
        System.out.printf("Swaps: %d\n", getSwaps());
        System.out.printf("Array Accesses: %d\n", getArrayAccesses());
        System.out.printf("Memory Allocations: %d bytes\n", getMemoryAllocations());
    }

    /**
     * Get all metrics as map
     */
    public Map<String, Long> getAllMetrics() {
        Map<String, Long> allMetrics = new HashMap<>(metrics);
        allMetrics.put("elapsedTime", getElapsedTime());
        return allMetrics;
    }
}