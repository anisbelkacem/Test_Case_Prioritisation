package de.uni_passau.fim.se2.se.test_prioritisation.encodings;

import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import de.uni_passau.fim.se2.se.test_prioritisation.utils.SelfTyped;

import static java.util.Objects.requireNonNull;

/**
 * A solution encoding that may be iteratively mutated by a search algorithm to find an optimal solution for a given problem.
 *
 * @param <E> the concrete type of the solution encoding
 * @apiNote Usually, it is desired that configurations of type {@code E} can only be transformed
 * into configurations of the same type {@code E}. This requirement can be enforced at compile time
 * by specifying a recursive type parameter, here: {@code E extends Encoding<E>}.
 */
public abstract class Encoding<E extends Encoding<E>> implements SelfTyped<E> {

    /**
     * The mutation operator used for an elementary transformation of a solution encoding.
     */
    private final Mutation<E> mutation;

    /**
     * Creates a new solution encoding that uses the given mutation operator.
     *
     * @param mutation the mutation operator to use
     */
    protected Encoding(final Mutation<E> mutation) {
        this.mutation = requireNonNull(mutation);
    }

    /**
     * Copy constructor.
     *
     * @param other the solution encoding to copy
     */
    protected Encoding(final Encoding<E> other) {
        this(other.mutation);
    }

    /**
     * Mutates the current solution encoding by performing an elementary transformation.
     *
     * @return the mutated solution encoding
     */
    public final E mutate() {
        return mutation.apply(self());
    }

    /**
     * Creates a deep copy of the current solution encoding.
     *
     * @return a deep copy of the current solution encoding
     */
    public abstract E deepCopy();

    public Mutation<E> getMutation() {
        return mutation;
    }
}
