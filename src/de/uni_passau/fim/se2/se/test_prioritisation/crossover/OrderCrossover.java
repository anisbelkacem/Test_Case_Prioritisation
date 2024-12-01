package de.uni_passau.fim.se2.se.test_prioritisation.crossover;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;

import java.util.*;

public class OrderCrossover implements Crossover<TestOrder> {

     /**
     * The internal source of randomness.
     */
    private final Random random;

    /**
     * Creates a new order crossover operator.
     *
     * @param random the internal source of randomness
     */
    public OrderCrossover(final Random random) {
        this.random = random;
    }

    /**
     * Combines two parent encodings to create a new offspring encoding using the order crossover operation.
     * The order crossover corresponds to a two-point crossover where the section between two random indices is copied
     * from the first parent and the remaining alleles are added in the order they appear in the second parent.
     * The resulting children must correspond to a valid test order encoding of size n that represents a permutation of tests
     * where each test value in the range [0, n-1] appears exactly once.
     *
     * @param parent1 the first parent encoding
     * @param parent2 the second parent encoding
     * @return the offspring encoding
     */
    @Override
    public TestOrder apply(TestOrder parent1, TestOrder parent2) {
        if(parent1 ==null || parent2 == null) {
            throw new NullPointerException("Both parents must not be null");
        }

        int[] positions1 = parent1.getPositions();
        int[] positions2 = parent2.getPositions();
        int n = positions1.length;

        if (n != positions2.length) {
            throw new IllegalArgumentException("Parent encodings must have the same size.");
        }

        int point1 = random.nextInt(n);
        int point2 = random.nextInt(n);
        if (point1 > point2) {
            int temp = point1;
            point1 = point2;
            point2 = temp;
        }

        int[] child = new int[n];
        Set<Integer> copied = new HashSet<>();
        for (int i = point1; i <= point2; i++) {
            child[i] = positions1[i];
            copied.add(positions1[i]);
        }
        
        int currentIndex = 0;
        for (int i = 0; i < n; i++) {
            if (!copied.contains(positions2[i])) {
                while (currentIndex >= point1 && currentIndex <= point2) {
                    currentIndex++;
                }
                child[currentIndex++] = positions2[i];
            }
        }

        return new TestOrder(parent1.getMutation(), child);
    }
}
