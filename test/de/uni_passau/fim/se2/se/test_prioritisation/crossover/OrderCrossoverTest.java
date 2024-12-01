package de.uni_passau.fim.se2.se.test_prioritisation.crossover;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;

public class OrderCrossoverTest {

    private OrderCrossover Crossover;
    private Random random;
    private Mutation<TestOrder> Mutation ;

    @BeforeEach
    void setUp() {
        random = new Random(); 
        Crossover = new OrderCrossover(random);
        Mutation = testOrder -> testOrder;
        
    }

    @Test
    void testValidParents() {
        int[] parent1Positions = {0, 1, 2, 3};
        int[] parent2Positions = {3, 2, 1, 0};

        TestOrder parent1 = new TestOrder(Mutation, parent1Positions);
        TestOrder parent2 = new TestOrder(Mutation, parent2Positions);

        TestOrder offspring = Crossover.apply(parent1, parent2);
        assertNotNull(offspring, "Offspring should not be null");
        assertTrue(TestOrder.isValid(offspring.getPositions()) ,"Offspring should be a valid encoding");
    }

    @Test
    void testTraitInheritance() {
        int[] parent1Positions = {0, 1, 2, 3};
        int[] parent2Positions = {3, 2, 1, 0};

        TestOrder parent1 = new TestOrder(Mutation, parent1Positions);
        TestOrder parent2 = new TestOrder(Mutation, parent2Positions);
        TestOrder offspring = Crossover.apply(parent1, parent2);

        assertNotEquals(parent1, offspring, "Offspring should not be identical to Parent1");
        assertNotEquals(parent2, offspring, "Offspring should not be identical to Parent2");

        assertTrue(hasTraitsFromBothParents(parent1Positions, parent2Positions, offspring.getPositions()),
                   "Offspring should have traits from both parents");
    }
    @Test 
    void testWithParentsWithoneElemet() {
        int[] parent1Positions = {0};
        int[] parent2Positions = {0};

        TestOrder parent1 = new TestOrder(Mutation, parent1Positions);
        TestOrder parent2 = new TestOrder(Mutation, parent2Positions);
        TestOrder offspring = Crossover.apply(parent1, parent2);
        assertNotNull(offspring);
        assertArrayEquals(new int[]{0},offspring.getPositions());
    }
    @Test
    void testRandomness() {
        int[] parent1Positions = {0, 1, 2, 3};
        int[] parent2Positions = {3, 2, 1, 0};

        TestOrder parent1 = new TestOrder(Mutation, parent1Positions);
        TestOrder parent2 = new TestOrder(Mutation, parent2Positions);
        TestOrder offspring1 = Crossover.apply(parent1, parent2);
        TestOrder offspring2 = Crossover.apply(parent1, parent2);

        assertNotEquals(offspring1.getPositions(), offspring2.getPositions(),
                        "Offspring should not always be identical due to randomness");
    }

    @Test
    void testInvalidInputs() {
        int[] parent1Positions = {0, 1, 2, 3};
        TestOrder parent1 = new TestOrder(Mutation, parent1Positions);
        int[] parent2Positions = {0, 1, 2};
        TestOrder parent2 = new TestOrder(Mutation, parent2Positions);
        assertThrows(NullPointerException.class, () -> Crossover.apply(parent1, null),
                     "Crossover should throw an exception for null parents");
        
        assertThrows(NullPointerException.class, () -> Crossover.apply(null, parent1),
        "Crossover should throw an exception for null parents"); 
        assertThrows(IllegalArgumentException.class, () -> Crossover.apply(parent1, parent2),
                     "Crossover should throw an exception for invalid parents");
    }
    

    private boolean hasTraitsFromBothParents(int[] parent1, int[] parent2, int[] offspring) {
        return Arrays.stream(offspring).anyMatch(pos -> Arrays.stream(parent1).anyMatch(p1 -> p1 == pos)) &&
               Arrays.stream(offspring).anyMatch(pos -> Arrays.stream(parent2).anyMatch(p2 -> p2 == pos));
    }
}
