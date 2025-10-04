package algorithms;

import metrics.PerformanceTracker;

/**
 * Optimized Insertion Sort Algorithm with enhancements for nearly-sorted data
 *
 * Time Complexity Analysis:
 * - Best Case: Θ(n) when array is already sorted (with early termination)
 * - Worst Case: Θ(n²) when array is reverse sorted
 * - Average Case: Θ(n²) for random data
 * - Nearly-sorted: O(n log n) with binary search optimization
 *
 * Space Complexity: Θ(1) - in-place sorting with O(1) auxiliary space
 *
 * Optimizations implemented:
 * 1. Binary search for insertion position in nearly-sorted data
 * 2. Early termination for already sorted arrays
 * 3. System.arraycopy for efficient element shifting
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
     * Optimized insertion sort with early termination and binary search
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

        // Handle edge cases efficiently
        if (array.length <= 1) {
            tracker.stopTimer();
            return array.clone();
        }

        int[] result = array.clone();

        // Optimization: Early check for already sorted array
        if (isAlreadySorted(result)) {
            tracker.stopTimer();
            return result;
        }

        // Use optimized insertion sort with binary search
        optimizedInsertionSort(result);

        tracker.stopTimer();
        return result;
    }

    /**
     * Optimized insertion sort using binary search for insertion position
     * Reduces comparisons in nearly-sorted arrays from O(n) to O(log n) per element
     */
    private void optimizedInsertionSort(int[] array) {
        int n = array.length;

        for (int i = 1; i < n; i++) {
            int key = array[i];
            tracker.recordArrayAccess(); // Access array[i]

            // Use binary search to find insertion position (optimization for nearly-sorted data)
            int insertionPos = binarySearchPosition(array, 0, i - 1, key);

            // Only shift elements if necessary
            if (insertionPos < i) {
                // Efficient element shifting using System.arraycopy
                System.arraycopy(array, insertionPos, array, insertionPos + 1, i - insertionPos);
                tracker.recordArrayAccess(2 * (i - insertionPos)); // Estimate array accesses

                array[insertionPos] = key;
                tracker.recordArrayAccess(); // Write to final position
            }
            // If insertionPos == i, element is already in correct position (optimization)
        }
    }

    /**
     * Binary search to find insertion position for key in sorted subarray [left, right]
     * Time Complexity: O(log n) per element vs O(n) in traditional implementation
     *
     * @param array the array being sorted
     * @param left left boundary of search range
     * @param right right boundary of search range
     * @param key the element to insert
     * @return position where key should be inserted
     */
    private int binarySearchPosition(int[] array, int left, int right, int key) {
        while (left <= right) {
            int mid = left + (right - left) / 2; // Avoid integer overflow
            tracker.recordArrayAccess(); // Access array[mid]
            tracker.recordComparison(); // Comparison in while condition

            if (array[mid] == key) {
                return mid + 1; // Maintain stability: insert after equal elements
            } else if (array[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    /**
     * Check if array is already sorted - early termination optimization
     * Time Complexity: O(n) but can save O(n²) operations
     */
    private boolean isAlreadySorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            tracker.recordComparison();
            tracker.recordArrayAccess(2); // Access array[i] and array[i+1]
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Traditional insertion sort implementation for performance comparison
     * Demonstrates the improvement from optimizations
     */
    public int[] traditionalSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }

        tracker.reset();
        tracker.startTimer();

        if (array.length <= 1) {
            tracker.stopTimer();
            return array.clone();
        }

        int[] result = array.clone();
        performTraditionalInsertionSort(result);

        tracker.stopTimer();
        return result;
    }

    /**
     * Traditional insertion sort without optimizations
     * Used for comparative performance analysis
     */
    private void performTraditionalInsertionSort(int[] array) {
        int n = array.length;

        for (int i = 1; i < n; i++) {
            int key = array[i];
            tracker.recordArrayAccess();

            int j = i - 1;

            // Linear search for insertion position (O(n) per element)
            while (j >= 0 && array[j] > key) {
                tracker.recordComparison(2); // Two comparisons per iteration
                array[j + 1] = array[j];
                tracker.recordArrayAccess(2);
                tracker.recordSwap();
                j--;
            }
            tracker.recordComparison(); // Final comparison

            array[j + 1] = key;
            tracker.recordArrayAccess();
        }
    }

    public PerformanceTracker getPerformanceTracker() {
        return tracker;
    }
}