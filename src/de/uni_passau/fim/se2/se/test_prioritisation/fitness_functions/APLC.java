package de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions;

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
        throw new UnsupportedOperationException("Implement me");
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
        throw new UnsupportedOperationException("Implement me");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double maximise(TestOrder encoding) throws NullPointerException {
        throw new UnsupportedOperationException("Implement me");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double minimise(TestOrder encoding) throws NullPointerException {
        throw new UnsupportedOperationException("Implement me");

    }
}
