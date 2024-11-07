package de.uni_passau.fim.se2.se.test_prioritisation.crossover;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;

import java.util.function.BinaryOperator;

/**
 * A crossover operator combines two parent encodings to create a new offspring encoding.
 * Crossover operations are used in genetic algorithms to exchange genetic information between two parents
 *
 * @param <E> the type of the encodings this operator works on
 */
public interface Crossover<E extends Encoding<E>> extends BinaryOperator<E> {

    /**
     * Combines two parent encodings to create a new offspring encoding.
     *
     * @param parent1 the first parent encoding
     * @param parent2 the second parent encoding
     * @return the offspring encoding
     */
    E apply(final E parent1, final E parent2);
}
