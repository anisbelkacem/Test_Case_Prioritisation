package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SimulatedAnnealingTest {

    private StoppingCondition stoppingCondition;
    private EncodingGenerator<MockEncoding> encodingGenerator;
    private FitnessFunction<MockEncoding> fitnessFunction;
    private Random random;
    private SimulatedAnnealing<MockEncoding> simulatedAnnealing;

    @BeforeEach
    void setUp() {
        // Ensure that all necessary objects are mocked properly
        stoppingCondition = mock(StoppingCondition.class);
        encodingGenerator = mock(EncodingGenerator.class);
        fitnessFunction = mock(FitnessFunction.class);
        random = mock(Random.class);

        // Initialize the SimulatedAnnealing with mocked dependencies
        simulatedAnnealing = new SimulatedAnnealing<>(
                stoppingCondition,
                encodingGenerator,
                fitnessFunction,
                5,  // degrees of freedom (for example)
                random
        );
    }

    @Test
    void testFindSolution() {
        // Create the mock mutation object
        Mutation<MockEncoding> mockMutation = mock(Mutation.class);

        // Create mock Encoding objects with mutation
        MockEncoding initialSolution = new MockEncoding(mockMutation);
        MockEncoding mutatedSolution = new MockEncoding(mockMutation);

        // Mock the behavior of encoding generator to return initial solution
        when(encodingGenerator.get()).thenReturn(initialSolution);

        // Mock fitness function to return a fitness value for the initial solution
        when(fitnessFunction.maximise(initialSolution)).thenReturn(10.0);

        // Set up behavior for mutated solution and fitness evaluation
        when(fitnessFunction.maximise(mutatedSolution)).thenReturn(8.0);

        // Mock stopping condition to stop after 3 iterations
        when(stoppingCondition.searchMustStop()).thenReturn(false, false, true);

        // Mock random behavior for acceptance of worse solutions
        when(random.nextDouble()).thenReturn(0.5);

        // Run the simulated annealing algorithm
        MockEncoding bestSolution = simulatedAnnealing.findSolution();

        // Assert that the best solution is the initial one with the highest fitness
        assertEquals(initialSolution, bestSolution, "The best solution should be the one with the highest fitness.");

        // Verify that methods were called on mock objects
        verify(encodingGenerator, times(1)).get();  // Verify encoding generator is called once
        verify(fitnessFunction, atLeast(1)).maximise(any());  // Verify fitness function is called at least once
        verify(random, atLeast(1)).nextDouble();  // Verify random.nextDouble() is called
        verify(stoppingCondition, atLeast(3)).searchMustStop();  // Verify stopping condition check happens at least 3 times
    }

    // A mock encoding class for testing
    static class MockEncoding extends Encoding<MockEncoding> {

        private final Mutation<MockEncoding> mutation;

        public MockEncoding(Mutation<MockEncoding> mutation) {
            super(mutation);
            this.mutation = mutation;
        }

        @Override
        public MockEncoding self() {
            return this;
        }

        @Override
        public MockEncoding deepCopy() {
            return new MockEncoding(this.mutation);
        }

        @Override
        public MockEncoding mutate() {
            return this;
        }

        @Override
        public Mutation<MockEncoding> getMutation() {
            return this.mutation;
        }
    }
}
