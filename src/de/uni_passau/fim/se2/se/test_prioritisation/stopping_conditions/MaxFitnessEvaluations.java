package de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions;

/**
 * Stopping condition that stops the search after a specified number of fitness evaluations.
 */
public class MaxFitnessEvaluations implements StoppingCondition {
    private final int maxFitnessEvaluations;
    private int fitnessEvaluationCount;

    public MaxFitnessEvaluations(final int maxFitnessEvaluations) {
        if (maxFitnessEvaluations <= 0) {
            throw new IllegalArgumentException("Maximum fitness evaluations must be greater than zero.");
        }
        this.maxFitnessEvaluations = maxFitnessEvaluations;
        this.fitnessEvaluationCount = 0; 
    }

    @Override
    public void notifySearchStarted() {
        fitnessEvaluationCount = 0;
    }

    @Override
    public void notifyFitnessEvaluation() {
        fitnessEvaluationCount++;
    }

    @Override
    public boolean searchMustStop() {
        return fitnessEvaluationCount >= maxFitnessEvaluations;
    }

    @Override
    public double getProgress() {
        return Math.min(1.0, (double) fitnessEvaluationCount / maxFitnessEvaluations);
    }
}
