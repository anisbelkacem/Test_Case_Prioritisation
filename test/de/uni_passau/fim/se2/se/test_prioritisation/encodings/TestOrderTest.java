package de.uni_passau.fim.se2.se.test_prioritisation.encodings;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;

public class TestOrderTest {
    private Mutation<TestOrder> Mutation;

    @BeforeEach
    void setUp() {
        Mutation = testOrder -> testOrder; 
    }
    @Test
    void TestOrderGenerator() {
        int[] validPositions = {0, 1, 2, 3};
        int[] invalidPositions1 = {1, 1, 2, 3};
        int[] invalidPositions2 = {4, 1, 2, 3};
        int[] invalidPositions3 = {0, -1, 2, 3};
        int[] invalidTest = {};
        TestOrder testOrder = new TestOrder(Mutation, validPositions);
        assertEquals(4, testOrder.size());
        assertEquals(true, TestOrder.isValid(validPositions));
        assertNotEquals(true, TestOrder.isValid(invalidPositions1));
        assertNotEquals(true, TestOrder.isValid(invalidPositions2));
        assertNotEquals(true, TestOrder.isValid(invalidPositions3));
        assertNotEquals(true, TestOrder.isValid(invalidTest));
        assertNotEquals(true, TestOrder.isValid(null));
        assertArrayEquals(validPositions, testOrder.getPositions(), "Positions should match the original array");
        assertSame(testOrder, testOrder.self());

    }
}
