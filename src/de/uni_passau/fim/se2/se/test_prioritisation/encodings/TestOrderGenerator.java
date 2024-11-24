package de.uni_passau.fim.se2.se.test_prioritisation.encodings;

import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;

import java.util.Random;

/**
 * A generator for random test case orderings of a regression test suite. In the literature, indices
 * would start at 1. However, we let them start at 0 as this simplifies the implementation. The
 * highest index is given by the number of test cases minus 1. The range of indices is contiguous.
 */
public class TestOrderGenerator implements EncodingGenerator<TestOrder> {
    private final Random random;
    private final Mutation<TestOrder> mutation;
    private final int testCases;
        /**
         * Creates a new test order generator with the given mutation and number of test cases.
         *
         * @param random     the source of randomness
         * @param mutation   the elementary transformation that the generated orderings will use
         * @param testCases  the number of test cases in the ordering
         */
        public TestOrderGenerator(final Random random, final Mutation<TestOrder> mutation, final int testCases) {
            if (random == null) {
                throw new IllegalArgumentException("Random cannot be null.");
            }
            if (mutation == null) {
                throw new IllegalArgumentException("Mutation cannot be null.");
            }
            if (testCases <= 0) {
                throw new IllegalArgumentException("Number of test cases must be greater than 0.");
            }
            this.random = random;
            this.mutation = mutation;
            this.testCases = testCases;
        }
    
        /**
         * Creates and returns a random permutation of test cases.
         *
         * @return random test case ordering
         */
        @Override
        public TestOrder get() {
            int[] positions = new int[testCases];
            for (int i = 0; i < testCases; i++) {
                positions[i] = i;
            }
    
            for (int i = positions.length - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                int temp = positions[i];
                positions[i] = positions[j];
                positions[j] = temp;
            }
    
            return new TestOrder(mutation, positions);
        }
}
