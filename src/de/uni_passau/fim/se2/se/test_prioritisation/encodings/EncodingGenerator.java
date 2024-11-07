package de.uni_passau.fim.se2.se.test_prioritisation.encodings;

import java.util.function.Supplier;

/**
 * Common functionality for all generators of {@code Encoding}s.
 *
 * @param <E> the type of encoding generated
 */
public interface EncodingGenerator<E extends Encoding<E>> extends Supplier<E> {

    /**
     * Creates and returns a random solution encoding, which must be a valid and admissible solution of the problem at hand.
     *
     * @return a random solution encoding
     */
    E get();
}
