package de.uni_passau.fim.se2.se.test_prioritisation.mutations;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ShiftToBeginningMutationTest {

    private Random random;
    private ShiftToBeginningMutation mutation;

    @BeforeEach
    void setUp() {
        random = new Random(50);
        mutation = new ShiftToBeginningMutation(random);
    }

    @Test
    void testConstructor() {
        assertNotNull(mutation, "ShiftToBeginningMutation should be successfully created with a valid Random instance.");
    }

    @Test
    void testApplyWithoutEncoding() {
        assertThrows(NullPointerException.class, () -> {
            mutation.apply(null);
        });
    }
    @Test
    void testApplyValidEncoding() {
        int[] initialPositions = {0, 1, 2, 3, 4};
        TestOrder testOrder = new TestOrder(mutation, initialPositions);

        TestOrder mutatedOrder = mutation.apply(testOrder);

        assertEquals(initialPositions.length, mutatedOrder.getPositions().length,
                     "Mutated order should have the same number of elements as the original.");

        int[] mutatedPositions = mutatedOrder.getPositions();
        for (int pos : initialPositions) {
            assertTrue(contains(mutatedPositions, pos), "Mutated order should contain all original elements.");
        }
    }

    @Test
    void testApplySingleElementEncoding() {
        int[] singlePosition = {0};
        TestOrder testOrder = new TestOrder(mutation, singlePosition);

        TestOrder mutatedOrder = mutation.apply(testOrder);
        assertArrayEquals(singlePosition, mutatedOrder.getPositions(),
                          "Mutation on a single-element test order should result in the same order.");
    }

    private boolean contains(int[] array, int value) {
        for (int element : array) {
            if (element == value) {
                return true;
            }
        }
        return false;
    }
}
