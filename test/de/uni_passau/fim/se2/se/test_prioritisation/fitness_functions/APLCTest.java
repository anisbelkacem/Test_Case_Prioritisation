package de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class APLCTest {

    private boolean[][] coverageMatrix;
    private APLC aplc;
    private Mutation<TestOrder> Mutation ;

    @BeforeEach
    void setUp() {
        coverageMatrix = new boolean[][]{
                {true, false, true},
                {true, true, false},
                {false, true, true}
        };
        aplc = new APLC(coverageMatrix);
        Mutation = testOrder -> testOrder;
    }

    @Test
    void testConstructorWithValidMatrix() {
        assertNotNull(aplc, "APLC should be created successfully with a valid coverage matrix.");
    }

    @Test
    void testConstructorWithInvalidMatrix() {
        assertThrows(IllegalArgumentException.class, () -> new APLC(null),
                     "Constructor should throw IllegalArgumentException for a null coverage matrix.");
        assertThrows(IllegalArgumentException.class, () -> new APLC(new boolean[0][]),
                     "Constructor should throw IllegalArgumentException for an empty coverage matrix.");
    }

    @Test
    void testApplyAsDoubleWithValidOrder() {
        int[] testOrderPositions = {0, 1, 2}; // Test order to evaluate
        TestOrder testOrder = new TestOrder(Mutation, testOrderPositions);

        double aplcValue = aplc.applyAsDouble(testOrder);

        assertTrue(aplcValue >= 0.0 && aplcValue <= 1.0,
                   "APLC value should be between 0.0 and 1.0.");
    }

    @Test
    void testApplyAsDoubleWithNullOrder() {
        assertThrows(NullPointerException.class, () -> aplc.applyAsDouble(null),
                     "applyAsDouble should throw NullPointerException for a null test order.");
    }

    @Test
    void testApplyAsDoubleWithNoLinesCovered() {
        
        coverageMatrix = new boolean[][]{
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };
        aplc = new APLC(coverageMatrix);
        int[] testOrderPositions = {0, 1, 2}; 
        TestOrder testOrder = new TestOrder(Mutation, testOrderPositions);
        double aplcValue = aplc.applyAsDouble(testOrder);
        assertEquals(1.166666666667, aplcValue, 0.0001, "APLC should return a value based on the formula when no lines are covered.");
    }


    @Test
    void testMaximise() {
        int[] testOrderPositions = {0, 1, 2};
        TestOrder testOrder = new TestOrder(Mutation, testOrderPositions);

        double maxFitness = aplc.maximise(testOrder);

        assertEquals(aplc.applyAsDouble(testOrder), maxFitness,
                     "maximise should return the same value as applyAsDouble.");
    }

    @Test
    void testMinimise() {
        int[] testOrderPositions = {0, 1, 2};
        TestOrder testOrder = new TestOrder(Mutation, testOrderPositions);

        double minFitness = aplc.minimise(testOrder);

        assertEquals(1 - aplc.applyAsDouble(testOrder), minFitness,
                     "minimise should return 1 minus the value of applyAsDouble.");
    }
}
