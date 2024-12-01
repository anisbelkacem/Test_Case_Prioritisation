package de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxFitnessEvaluationsTest {

    private MaxFitnessEvaluations stoppingCondition;

    @BeforeEach
    void setUp() {
        stoppingCondition = new MaxFitnessEvaluations(10);
    }

    @Test
    void testConstructorWithValidValue() {
        MaxFitnessEvaluations condition = new MaxFitnessEvaluations(5);
        assertNotNull(condition, "Stopping condition should be created successfully with valid arguments.");
    }

    @Test
    void testConstructorWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> new MaxFitnessEvaluations(0),
                     "Constructor should throw IllegalArgumentException for zero evaluations.");
        assertThrows(IllegalArgumentException.class, () -> new MaxFitnessEvaluations(-1),
                     "Constructor should throw IllegalArgumentException for negative evaluations.");
    }

    @Test
    void testNotifySearchStarted() {

        stoppingCondition.notifyFitnessEvaluation();
        stoppingCondition.notifyFitnessEvaluation();

        stoppingCondition.notifySearchStarted();

        assertEquals(0, stoppingCondition.getProgress(), 
                     "After restarting the search, progress should be reset to 0.");
    }

    @Test
    void testNotifyFitnessEvaluation() {
        stoppingCondition.notifySearchStarted();

        stoppingCondition.notifyFitnessEvaluation();
        stoppingCondition.notifyFitnessEvaluation();

        assertEquals(0.2, stoppingCondition.getProgress(), 
                     "Progress should reflect the correct fraction of evaluations.");
        assertFalse(stoppingCondition.searchMustStop(),
                    "Search must not stop if evaluations are below the maximum.");
    }

    @Test
    void testSearchMustStop() {
        stoppingCondition.notifySearchStarted();
        for (int i = 0; i < 10; i++) {
            stoppingCondition.notifyFitnessEvaluation();
        }
        assertTrue(stoppingCondition.searchMustStop(), 
                   "Search must stop after the maximum number of evaluations is reached.");
    }

    @Test
    void testGetProgressBeforeMaximum() {
        
        stoppingCondition.notifySearchStarted();
        stoppingCondition.notifyFitnessEvaluation(); // 1/10 = 0.1
        stoppingCondition.notifyFitnessEvaluation(); // 2/10 = 0.2
        double progress = stoppingCondition.getProgress();

        
        assertEquals(0.2, progress, 0.01, 
                     "Progress should correctly reflect the ratio of evaluations to the maximum.");
    }

    @Test
    void testGetProgressAtMaximum() {
        stoppingCondition.notifySearchStarted();
        for (int i = 0; i < 10; i++) {
            stoppingCondition.notifyFitnessEvaluation();
        }

        double progress = stoppingCondition.getProgress();

        assertEquals(1.0, progress, 
                     "Progress should be 1.0 when the maximum number of evaluations is reached.");
    }

    @Test
    void testGetProgressBeyondMaximum() {
        stoppingCondition.notifySearchStarted();
        for (int i = 0; i < 12; i++) {
            stoppingCondition.notifyFitnessEvaluation();
        }

        double progress = stoppingCondition.getProgress();
        assertEquals(1.0, progress, 
                     "Progress should be capped at 1.0 even if the evaluation count exceeds the maximum.");
        assertTrue(stoppingCondition.searchMustStop(), 
                   "Search must stop if the evaluation count exceeds the maximum.");
    }
}
