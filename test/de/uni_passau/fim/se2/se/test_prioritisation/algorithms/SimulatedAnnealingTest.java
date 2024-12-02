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
        
        stoppingCondition = mock(StoppingCondition.class);
        encodingGenerator = mock(EncodingGenerator.class);
        fitnessFunction = mock(FitnessFunction.class);
        random = mock(Random.class);

        
        simulatedAnnealing = new SimulatedAnnealing<>(
                stoppingCondition,
                encodingGenerator,
                fitnessFunction,
                5,
                random
        );
    }

    @Test
    void testFindSolution() {
        Mutation<MockEncoding> mockMutation = mock(Mutation.class);
        MockEncoding initialSolution = new MockEncoding(mockMutation);
        MockEncoding mutatedSolution = new MockEncoding(mockMutation);

        when(encodingGenerator.get()).thenReturn(initialSolution);
        when(fitnessFunction.maximise(initialSolution)).thenReturn(10.0);
        when(fitnessFunction.maximise(mutatedSolution)).thenReturn(12.0);
        when(fitnessFunction.maximise(mutatedSolution)).thenReturn(20.0);
        when(stoppingCondition.searchMustStop()).thenReturn(false, false, false,true);
        when(random.nextDouble()).thenReturn(0.1);
        MockEncoding bestSolution = simulatedAnnealing.findSolution();
        assertEquals(initialSolution, bestSolution, "The best solution should be the one with the highest fitness.");

        verify(encodingGenerator, times(1)).get(); 
        verify(fitnessFunction, atLeast(1)).maximise(any()); 
        verify(random, atLeast(1)).nextDouble(); 
        verify(stoppingCondition, atLeast(3)).searchMustStop(); 
    }
    
    @Test
    void testUpdateCurrentSolutionOnly() {
        Mutation<MockEncoding> mockMutation = mock(Mutation.class);
        MockEncoding initialSolution = new MockEncoding(mockMutation);
        MockEncoding worseSolution = new MockEncoding(mockMutation);

        when(encodingGenerator.get()).thenReturn(initialSolution);
        when(fitnessFunction.maximise(initialSolution)).thenReturn(10.0); 
        when(fitnessFunction.maximise(worseSolution)).thenReturn(8.0);   
        when(stoppingCondition.searchMustStop()).thenReturn(false, true);
        when(random.nextDouble()).thenReturn(0.8); 

        MockEncoding bestSolution = simulatedAnnealing.findSolution();
        assertEquals(initialSolution, bestSolution, 
            "Best solution should not change when only the current solution is updated.");
        verify(encodingGenerator, times(1)).get();
        verify(fitnessFunction, atLeast(2)).maximise(any());
    }
    @Test
    void testUpdateBestSolutionOnly() {
        Mutation<MockEncoding> mockMutation = mock(Mutation.class);
        MockEncoding initialSolution = mock(MockEncoding.class); 
        MockEncoding betterSolution = mock(MockEncoding.class);  

        
        when(encodingGenerator.get()).thenReturn(initialSolution);
        when(fitnessFunction.maximise(initialSolution)).thenReturn(10.0); 
        when(fitnessFunction.maximise(betterSolution)).thenReturn(15.0); 
        when(initialSolution.deepCopy()).thenReturn(initialSolution);
        when(initialSolution.mutate()).thenReturn(betterSolution);
        when(stoppingCondition.searchMustStop()).thenReturn(false, true);

        MockEncoding bestSolution = simulatedAnnealing.findSolution();
        assertEquals(betterSolution, bestSolution,
            "Best solution should be updated to the better solution.");
        verify(encodingGenerator, times(1)).get();
        verify(fitnessFunction, times(2)).maximise(any());
        verify(stoppingCondition, times(2)).searchMustStop(); 
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
