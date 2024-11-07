package de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions;

/**
 * Stopping condition that stops the search after a specified number of fitness evaluations.
 */
public class MaxFitnessEvaluations implements StoppingCondition {

    public MaxFitnessEvaluations(final int maxFitnessEvaluations) {
        throw new UnsupportedOperationException("Implement me");
    }

    @Override
    public void notifySearchStarted() {
        throw new UnsupportedOperationException("Implement me");
    }

    @Override
    public void notifyFitnessEvaluation() {
        throw new UnsupportedOperationException("Implement me");
    }

    @Override
    public boolean searchMustStop() {
        throw new UnsupportedOperationException("Implement me");
    }

    @Override
    public double getProgress() {
        throw new UnsupportedOperationException("Implement me");
    }
}
