package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RandomWalkTest {

    private StoppingCondition stoppingCondition;
    private EncodingGenerator<TestOrder> encodingGenerator;
    private FitnessFunction<TestOrder> fitnessFunction;

    @BeforeEach
    void setUp() {
        stoppingCondition = mock(StoppingCondition.class);
        encodingGenerator = mock(EncodingGenerator.class);
        fitnessFunction = mock(FitnessFunction.class);
    }

    @Test
    void testFindSolution() {
        TestOrder testOrder1 = mock(TestOrder.class);
        TestOrder testOrder2 = mock(TestOrder.class);
        when(encodingGenerator.get()).thenReturn(testOrder1, testOrder2); 

        when(fitnessFunction.applyAsDouble(testOrder1)).thenReturn(0.5);
        when(fitnessFunction.applyAsDouble(testOrder2)).thenReturn(0.7);

        when(stoppingCondition.searchMustStop()).thenReturn(false, false, true); 
        RandomWalk<TestOrder> randomWalk = new RandomWalk<>(stoppingCondition, encodingGenerator, fitnessFunction);
        TestOrder bestSolution = randomWalk.findSolution();

        assertEquals(testOrder2, bestSolution, "Best solution should be the one with highest fitness.");
        verify(fitnessFunction, times(3)).applyAsDouble(any());  
        verify(stoppingCondition, times(3)).searchMustStop();  
    }

    @Test
    void testStoppingConditionIsTriggered() {
        TestOrder testOrder1 = mock(TestOrder.class);
        when(encodingGenerator.get()).thenReturn(testOrder1);  

        when(stoppingCondition.searchMustStop()).thenReturn(false, false, true); 
        when(fitnessFunction.applyAsDouble(testOrder1)).thenReturn(0.5);  

        RandomWalk<TestOrder> randomWalk = new RandomWalk<>(stoppingCondition, encodingGenerator, fitnessFunction);
        randomWalk.findSolution();

        verify(stoppingCondition, times(3)).searchMustStop(); 
        verify(fitnessFunction, times(3)).applyAsDouble(any());  
    }

    @Test
    void testGetStoppingCondition() {
        RandomWalk<TestOrder> randomWalk = new RandomWalk<>(stoppingCondition, encodingGenerator, fitnessFunction);

        assertSame(stoppingCondition, randomWalk.getStoppingCondition(),
                "getStoppingCondition should return the same stopping condition instance that was passed to the constructor.");
    }

    @Test
    void testFindSolutionWithEmptyPopulation() {
        when(encodingGenerator.get()).thenReturn(null);  

        when(stoppingCondition.searchMustStop()).thenReturn(false, true);  

        RandomWalk<TestOrder> randomWalk = new RandomWalk<>(stoppingCondition, encodingGenerator, fitnessFunction);
        
        TestOrder bestSolution = randomWalk.findSolution();

        assertNull(bestSolution, "Best solution should be null when no solutions are available.");
    }
}
