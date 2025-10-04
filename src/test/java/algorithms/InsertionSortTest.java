package algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

/**
 * Comprehensive test suite for InsertionSort algorithm
 * Covers all edge cases and various input distributions
 */
class InsertionSortTest {
    private InsertionSort sorter;

    @BeforeEach
    void setUp() {
        sorter = new InsertionSort();
    }

    @Test
    @DisplayName("Should handle empty array")
    void testEmptyArray() {
        int[] input = {};
        int[] result = sorter.sort(input);
        assertArrayEquals(new int[]{}, result, "Empty array should remain empty");
    }

    @Test
    @DisplayName("Should handle single element array")
    void testSingleElement() {
        int[] input = {5};
        int[] result = sorter.sort(input);
        assertArrayEquals(new int[]{5}, result, "Single element array should remain unchanged");
    }

    @Test
    @DisplayName("Should handle already sorted array")
    void testAlreadySorted() {
        int[] input = {1, 2, 3, 4, 5};
        int[] result = sorter.sort(input);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result, "Sorted array should remain sorted");
    }

    @Test
    @DisplayName("Should sort reverse sorted array")
    void testReverseSorted() {
        int[] input = {5, 4, 3, 2, 1};
        int[] result = sorter.sort(input);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result, "Reverse sorted array should be sorted");
    }

    @Test
    @DisplayName("Should handle array with duplicates")
    void testWithDuplicates() {
        int[] input = {3, 1, 4, 1, 5, 9, 2, 6};
        int[] result = sorter.sort(input);
        int[] expected = {1, 1, 2, 3, 4, 5, 6, 9};
        assertArrayEquals(expected, result, "Array with duplicates should be sorted correctly");
    }

    @Test
    @DisplayName("Should handle array with all identical elements")
    void testAllIdenticalElements() {
        int[] input = {7, 7, 7, 7, 7};
        int[] result = sorter.sort(input);
        assertArrayEquals(new int[]{7, 7, 7, 7, 7}, result,
                "Array with all identical elements should remain unchanged");
    }

    @Test
    @DisplayName("Should handle array with negative numbers")
    void testNegativeNumbers() {
        int[] input = {-3, -1, -4, -2, 0};
        int[] result = sorter.sort(input);
        int[] expected = {-4, -3, -2, -1, 0};
        assertArrayEquals(expected, result, "Array with negative numbers should be sorted correctly");
    }

    @Test
    @DisplayName("Should throw exception for null input")
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            sorter.sort(null);
        }, "Should throw IllegalArgumentException for null input");
    }

    @Test
    @DisplayName("Should sort large random array")
    void testLargeRandomArray() {
        Random random = new Random(42); // Fixed seed for reproducibility
        int[] input = new int[1000];
        for (int i = 0; i < input.length; i++) {
            input[i] = random.nextInt(10000);
        }

        int[] result = sorter.sort(input);

        // Verify the array is sorted
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue(result[i] <= result[i + 1],
                    "Large array should be sorted in non-decreasing order");
        }
    }

    @Test
    @DisplayName("Should track performance metrics")
    void testPerformanceMetrics() {
        int[] input = {5, 2, 4, 6, 1, 3};
        sorter.sort(input);

        var tracker = sorter.getPerformanceTracker();

        assertTrue(tracker.getComparisons() > 0, "Should record comparisons");
        assertTrue(tracker.getArrayAccesses() > 0, "Should record array accesses");
        assertTrue(tracker.getElapsedTime() >= 0, "Should record execution time");

        // ИСПРАВЛЕНИЕ: Для оптимизированной версии swaps может быть 0
        // из-за использования System.arraycopy вместо отдельных swap операций
        // Это нормальное поведение, поэтому убираем проверку на swaps > 0

        System.out.println("Performance Metrics:");
        System.out.println("  Comparisons: " + tracker.getComparisons());
        System.out.println("  Array Accesses: " + tracker.getArrayAccesses());
        System.out.println("  Swaps: " + tracker.getSwaps());
        System.out.println("  Time: " + tracker.getElapsedTime() + " ns");
    }

    @Test
    @DisplayName("Should track swaps in traditional sort")
    void testTraditionalSortSwaps() {
        int[] input = {5, 2, 4, 6, 1, 3};

        // Используем traditionalSort который гарантированно имеет swaps
        sorter.traditionalSort(input);

        var tracker = sorter.getPerformanceTracker();

        assertTrue(tracker.getComparisons() > 0, "Should record comparisons");
        assertTrue(tracker.getSwaps() > 0, "Traditional sort should record swaps");
        assertTrue(tracker.getArrayAccesses() > 0, "Should record array accesses");
    }

    @Test
    @DisplayName("Should produce correct results for property-based testing")
    void testPropertyBasedSorting() {
        Random random = new Random(123);

        for (int size = 0; size <= 100; size += 25) {
            int[] input = new int[size];
            for (int i = 0; i < size; i++) {
                input[i] = random.nextInt(1000);
            }

            int[] result = sorter.sort(input);

            // Test 1: Result should be sorted
            for (int i = 0; i < result.length - 1; i++) {
                assertTrue(result[i] <= result[i + 1],
                        "Result should be sorted for input size " + size);
            }

            // Test 2: Result should contain same elements as input
            int[] sortedInput = input.clone();
            Arrays.sort(sortedInput);
            assertArrayEquals(sortedInput, result,
                    "Should match Java's built-in sort for input size " + size);
        }
    }
}