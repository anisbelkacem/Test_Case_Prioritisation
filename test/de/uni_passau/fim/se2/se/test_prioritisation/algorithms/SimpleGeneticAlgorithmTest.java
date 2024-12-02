package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import de.uni_passau.fim.se2.se.test_prioritisation.crossover.Crossover;
import de.uni_passau.fim.se2.se.test_prioritisation.parent_selection.ParentSelection;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class SimpleGeneticAlgorithmTest {

    private StoppingCondition stoppingCondition;
    private EncodingGenerator<MockEncoding> encodingGenerator;
    private FitnessFunction<MockEncoding> fitnessFunction;
    private Crossover<MockEncoding> crossover;
    private ParentSelection<MockEncoding> parentSelection;
    private Random random;
    private SimpleGeneticAlgorithm<MockEncoding> algorithm;

    @BeforeEach
    void setUp() {
        stoppingCondition = mock(StoppingCondition.class);
        encodingGenerator = mock(EncodingGenerator.class);
        fitnessFunction = mock(FitnessFunction.class);
        crossover = mock(Crossover.class);
        parentSelection = mock(ParentSelection.class);
        random = new Random();

        algorithm = new SimpleGeneticAlgorithm<>(
                stoppingCondition,
                encodingGenerator,
                fitnessFunction,
                crossover,
                parentSelection,
                random
        );
    }

    @Test
    void testFindSolution() {
        Mutation<MockEncoding> mockMutation = mock(Mutation.class);
        MockEncoding initialSolution = new MockEncoding(mockMutation);
        when(stoppingCondition.searchMustStop()).thenReturn(false).thenReturn(true); 
        when(encodingGenerator.get()).thenReturn(initialSolution); 
        when(fitnessFunction.applyAsDouble(any())).thenReturn(1.0); 
        when(parentSelection.selectParent(any())).thenReturn(initialSolution); 
        when(crossover.apply(any(), any())).thenReturn(initialSolution); 

        MockEncoding solution = algorithm.findSolution();
        //assertNotNull(solution, "The solution should not be null");
    }

    

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