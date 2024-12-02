package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RandomSearchTest {

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
        when(fitnessFunction.maximise(testOrder1)).thenReturn(0.5);
        when(fitnessFunction.maximise(testOrder2)).thenReturn(0.7);

        when(stoppingCondition.searchMustStop()).thenReturn(false, false, true); 

        RandomSearch<TestOrder> randomSearch = new RandomSearch<>(stoppingCondition, encodingGenerator, fitnessFunction);
        TestOrder bestSolution = randomSearch.findSolution();

        assertEquals(testOrder2, bestSolution, "Best solution should be the one with highest fitness.");
        verify(fitnessFunction, times(2)).maximise(any());  
        verify(stoppingCondition, times(3)).searchMustStop(); 
    }

    @Test
    void testStoppingConditionIsTriggered() {
        TestOrder testOrder1 = mock(TestOrder.class);
        when(encodingGenerator.get()).thenReturn(testOrder1); 

        when(stoppingCondition.searchMustStop()).thenReturn(false, false, true); 
        when(fitnessFunction.maximise(testOrder1)).thenReturn(0.5); 

        RandomSearch<TestOrder> randomSearch = new RandomSearch<>(stoppingCondition, encodingGenerator, fitnessFunction);
        randomSearch.findSolution();

        verify(stoppingCondition, times(3)).searchMustStop();  
        verify(fitnessFunction, times(2)).maximise(any()); 
    }

    @Test
    void testGetStoppingCondition() {
        RandomSearch<TestOrder> randomSearch = new RandomSearch<>(stoppingCondition, encodingGenerator, fitnessFunction);

        assertSame(stoppingCondition, randomSearch.getStoppingCondition(),
                "getStoppingCondition should return the same stopping condition instance that was passed to the constructor.");
    }

    @Test
    void testFindSolutionWithEmptyPopulation() {
        when(encodingGenerator.get()).thenReturn(null);  

        when(stoppingCondition.searchMustStop()).thenReturn(false, true); 

        RandomSearch<TestOrder> randomSearch = new RandomSearch<>(stoppingCondition, encodingGenerator, fitnessFunction);
        
        TestOrder bestSolution = randomSearch.findSolution();

        assertNull(bestSolution, "Best solution should be null when no solutions are available.");
    }
}
