package de.uni_passau.fim.se2.se.test_prioritisation.encodings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;

public class TestOrderGeneratorTest {

    private Random random;
    private Mutation<TestOrder> mutation;
    private TestOrderGenerator generator;

    @BeforeEach
    void setUp() {
        random = new Random(50); 
        mutation = order -> order; 
        generator = new TestOrderGenerator(random, mutation, 5);
    }

    @Test
    void testConstructor() {
        assertNotNull(generator, "Generator should be created successfully with valid parameters.");
        assertThrows(IllegalArgumentException.class, 
                     () -> new TestOrderGenerator(null, mutation, 5),
                     "Constructor should throw IllegalArgumentException for null random.");
        assertThrows(IllegalArgumentException.class, 
                     () -> new TestOrderGenerator(random, null, 5),
                     "Constructor should throw IllegalArgumentException for null mutation.");
        assertThrows(IllegalArgumentException.class, 
                     () -> new TestOrderGenerator(random, mutation, 0),
                     "Constructor should throw IllegalArgumentException for zero test cases.");
        assertThrows(IllegalArgumentException.class, 
                     () -> new TestOrderGenerator(random, mutation, -1),
                     "Constructor should throw IllegalArgumentException for negative test cases.");
    }
    @Test
    void testGetReturnsValidOrder() {
        TestOrder order = generator.get();

        int[] positions = order.getPositions();
        assertEquals(5, positions.length, "Generated order should have the correct number of test cases.");

        
        boolean[] seen = new boolean[5];
        for (int pos : positions) {
            assertTrue(pos >= 0 && pos < 5, "Positions should be within the valid range.");
            assertFalse(seen[pos], "Position values should be unique.");
            seen[pos] = true;
        }
    }

    @Test
    void testGetRandomness() {
        TestOrder firstOrder = generator.get();
        TestOrder secondOrder = generator.get();
        assertNotEquals(firstOrder, secondOrder, "Two generated orders should likely differ due to randomness.");
    }
}
