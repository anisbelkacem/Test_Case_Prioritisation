package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;


import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;

/**
 * Represents a search strategy to find an (approximated) solution to a given problem.
 *
 * @param <E> the type of the solution encoding on which the search algorithm operates
 */
public interface SearchAlgorithm<E> {

    /**
     * Runs the search algorithm to find an admissible solution for the given search problem.
     * <p>
     * Every run must perform a new search and must be independent of the previous one.
     * In particular, it must be possible to call this method multiple times in a row.
     * Thus, implementations must ensure multiple runs do not interfere each other.
     *
     * @return the optimised solution
     */
    E findSolution();

    /**
     * Returns the stopping condition used by this algorithm
     *
     * @return the used stopping condition
     */
    StoppingCondition getStoppingCondition();

    /**
     * Notifies the observing stopping condition that the search has started.
     */
    default void notifySearchStarted() {
        getStoppingCondition().notifySearchStarted();
    }

    /**
     * Notifies the observing stopping condition that a fitness evaluation was performed.
     */
    default void notifyFitnessEvaluation() {
        getStoppingCondition().notifyFitnessEvaluation();
    }

    /**
     * Tells the search algorithm whether it has to stop due to an exhausted search budget.
     *
     * @return {@code true} if the search must stop, {@code false} otherwise
     */
    default boolean searchMustStop() {
        return getStoppingCondition().searchMustStop();
    }

    /**
     * Returns the fraction of the search budget already consumed by the search.
     *
     * @return the fraction of the search budget that has already been consumed
     */
    default double getProgress() {
        return getStoppingCondition().getProgress();
    }
}
