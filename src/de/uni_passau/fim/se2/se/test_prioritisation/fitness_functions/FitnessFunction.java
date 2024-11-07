package de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions;

import java.util.function.ToDoubleFunction;

/**
 * A fitness function maps a given solution encoding to a numeric value that represents its goodness.
 * Since we cast the test prioritisation problem as a minimisation problem, lower fitness values indicate better solutions.
 *
 * @param <E> the type of the solution encoding rated by this function
 */
public interface FitnessFunction<E> extends ToDoubleFunction<E> {

    /**
     * Calculates the fitness of the given encoding.
     *
     * @param encoding the encoding to calculate the fitness for
     * @return the fitness of the encoding
     * @throws NullPointerException if {@code null} is given
     */
    @Override
    double applyAsDouble(final E encoding) throws NullPointerException;

    /**
     * Calculates the fitness of the given encoding for maximisation algorithms.
     *
     * @param encoding the encoding to calculate the fitness for
     * @return the fitness of the encoding for maximisation algorithms
     */
    double maximise(final E encoding) throws NullPointerException;

    /**
     * Calculates the fitness of the given encoding for minimisation algorithms.
     *
     * @param encoding the encoding to calculate the fitness for
     * @return the fitness of the encoding for minimisation algorithms
     */
    double minimise(final E encoding) throws NullPointerException;
}
