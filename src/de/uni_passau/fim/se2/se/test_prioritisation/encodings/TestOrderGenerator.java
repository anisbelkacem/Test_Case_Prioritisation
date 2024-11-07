package de.uni_passau.fim.se2.se.test_prioritisation.encodings;

import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;

import java.util.Random;

/**
 * A generator for random test case orderings of a regression test suite. In the literature, indices
 * would start at 1. However, we let them start at 0 as this simplifies the implementation. The
 * highest index is given by the number of test cases minus 1. The range of indices is contiguous.
 */
public class TestOrderGenerator implements EncodingGenerator<TestOrder> {

    /**
     * Creates a new test order generator with the given mutation and number of test cases.
     *
     * @param random     the source of randomness
     * @param mutation   the elementary transformation that the generated orderings will use
     * @param testCases  the number of test cases in the ordering
     */
    public TestOrderGenerator(final Random random, final Mutation<TestOrder> mutation, final int testCases) {
        throw new UnsupportedOperationException("Implement me");
    }

    /**
     * Creates and returns a random permutation of test cases.
     *
     * @return random test case ordering
     */
    @Override
    public TestOrder get() {
        throw new UnsupportedOperationException("Implement me");
    }
}
