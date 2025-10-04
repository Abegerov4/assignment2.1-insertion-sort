package algorithms;

import metrics.PerformanceTracker;

/**
 * Insertion Sort Algorithm Implementation
 *
 * Time Complexity Analysis:
 * - Best Case: O(n) when array is already sorted
 * - Worst Case: O(n²) when array is reverse sorted
 * - Average Case: O(n²) for random data
 *
 * Space Complexity: O(1) - in-place sorting
 *
 * Optimizations: Basic implementation with performance tracking
 */
public class InsertionSort {
    private final PerformanceTracker tracker;

    public InsertionSort() {
        this.tracker = new PerformanceTracker();
    }

    public InsertionSort(PerformanceTracker tracker) {
        this.tracker = tracker;
    }

    /**
     * Sorts the input array using insertion sort algorithm
     *
     * @param array the array to be sorted
     * @return sorted array
     * @throws IllegalArgumentException if input array is null
     */
    public int[] sort(int[] array) {
        // Input validation
        if (array == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }

        tracker.reset();
        tracker.startTimer();

        // Handle edge cases
        if (array.length <= 1) {
            tracker.stopTimer();
            return array.clone();
        }

        int[] result = array.clone();
        performInsertionSort(result);

        tracker.stopTimer();
        return result;
    }

    /**
     * Core insertion sort algorithm implementation
     */
    private void performInsertionSort(int[] array) {
        int n = array.length;

        for (int i = 1; i < n; i++) {
            int key = array[i];
            tracker.recordArrayAccess(); // Access array[i]

            int j = i - 1;

            // Find insertion position and shift elements
            while (j >= 0 && array[j] > key) {
                tracker.recordComparison(2); // j >= 0 and array[j] > key
                array[j + 1] = array[j];
                tracker.recordArrayAccess(2); // Read array[j], write array[j+1]
                tracker.recordSwap();
                j--;
            }
            tracker.recordComparison(); // Final comparison that breaks loop

            array[j + 1] = key;
            tracker.recordArrayAccess(); // Write array[j+1]
        }
    }

    public PerformanceTracker getPerformanceTracker() {
        return tracker;
    }
}