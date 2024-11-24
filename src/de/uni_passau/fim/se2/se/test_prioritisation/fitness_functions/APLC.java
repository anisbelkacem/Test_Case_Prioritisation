package de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions;

import java.util.Arrays;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;


/**
 * The Average Percentage of Lines Covered (APLC) fitness function.
 */
public final class APLC implements FitnessFunction<TestOrder> {

    /**
     * The coverage matrix to be used when computing the APLC metric.
     */
    private final boolean[][] coverageMatrix;

    /**
     * Creates a new APLC fitness function with the given coverage matrix.
     *
     * @param coverageMatrix the coverage matrix to be used when computing the APLC metric
     */
    public APLC(final boolean[][] coverageMatrix) {
        if (coverageMatrix == null || coverageMatrix.length == 0) {
            throw new IllegalArgumentException("Coverage matrix cannot be null or empty.");
        }
        this.coverageMatrix = coverageMatrix;
    }


    /**
     * Computes and returns the APLC for the given order of test cases.
     * Orderings that achieve a higher rate of coverage are rewarded with higher values.
     * The APLC ranges between 0.0 and 1.0.
     *
     * @param testOrder the proposed test order for which the fitness value will be computed
     * @return the APLC value of the given test order
     * @throws NullPointerException if {@code null} is given
     */
    @Override
public double applyAsDouble(final TestOrder testOrder) throws NullPointerException {
    if (testOrder == null) {
        throw new NullPointerException("Test order cannot be null.");
    }

    int[] positions = testOrder.getPositions();
    int n = positions.length; 
    int m = coverageMatrix[0].length; 

    int[] firstCoverage = new int[m];
    Arrays.fill(firstCoverage, Integer.MAX_VALUE);

    for (int testIndex = 0; testIndex < n; testIndex++) {
        int testCase = positions[testIndex];
        for (int line = 0; line < m; line++) {
            if (coverageMatrix[testCase][line]) {
                firstCoverage[line] = Math.min(firstCoverage[line], testIndex + 1); 
            }
        }
    }

    int totalTL = 0;
    for (int line = 0; line < m; line++) {
        if (firstCoverage[line] != Integer.MAX_VALUE) {
            totalTL += firstCoverage[line];
        }
    }

    return 1.0 - (1.0 / (n * m)) * totalTL + (1.0 / (2 * n));
}



    /**
     * {@inheritDoc}
     */
    @Override
    public double maximise(TestOrder encoding) throws NullPointerException {
        return applyAsDouble(encoding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double minimise(TestOrder encoding) throws NullPointerException {
        return -applyAsDouble(encoding);
    }
}
