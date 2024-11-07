package de.uni_passau.fim.se2.se.test_prioritisation.parent_selection;

import java.util.List;

/**
 * A parent selection operator selects parents from a population to be evolved in the current generation of an evolutionary algorithm.
 *
 * @param <E> the type of the solution encodings the evolutionary algorithm works on
 */
public interface ParentSelection<E> {

    /**
     * Selects a single parent from a population to be evolved in the current generation of an evolutionary algorithm.
     *
     * @param population the population from which to select parents
     * @return the selected parent
     */
    E selectParent(List<E> population);
}
