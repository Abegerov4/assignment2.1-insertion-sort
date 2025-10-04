# Insertion Sort Algorithm Implementation

## Assignment 2: Algorithmic Analysis and Peer Code Review

### Algorithm Overview
Optimized Insertion Sort implementation with enhancements for nearly-sorted data, including binary search insertion and early termination.

### Complexity Analysis

#### Time Complexity
- **Best Case**: Θ(n) - already sorted array with early termination
- **Worst Case**: Θ(n²) - reverse sorted array
- **Average Case**: Θ(n²) - random data distribution
- **Nearly-sorted**: O(n log n) - with binary search optimization

#### Space Complexity
- **Auxiliary Space**: Θ(1) - in-place sorting algorithm
- **Total Space**: Θ(n) - including input storage

### Features
- ✅ Comprehensive performance metrics tracking
- ✅ Binary search optimization for nearly-sorted data
- ✅ Early termination for already sorted arrays
- ✅ Traditional vs optimized implementation comparison
- ✅ Extensive unit test coverage
- ✅ CLI benchmarking interface


### Git Branch Strategy
- `main` - production releases (tagged v0.1, v1.0)
- `feature/algorithm` - main algorithm implementation
- `feature/metrics` - performance tracking system
- `feature/testing` - unit tests and validation
- `feature/cli` - command-line interface
- `feature/optimization` - performance improvements

### Build & Run
```bash
# Compile project
mvn compile

# Run tests
mvn test

# Run benchmarks
mvn exec:java -Dexec.mainClass="cli.BenchmarkRunner"

# Run specific benchmark
mvn exec:java -Dexec.mainClass="cli.BenchmarkRunner" -Dexec.args="1000 random"