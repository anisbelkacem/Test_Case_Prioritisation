package de.uni_passau.fim.se2.se.test_prioritisation.utils;

/**
 * An interface for classes that implement the so called Curiously Recurring Template pattern (also
 * known as F-bounded polymorphism). This pattern is useful to make up for the lack of self types in
 * Java while still maintaining type-safety, albeit at the expense of clean and short class
 * signatures. (The alternative is to resort to unchecked type casts or unbounded wildcards {@code
 * <?>} where a self type would be more appropriate.) Self types are commonly used in abstract super
 * classes or interfaces to refer to the subtype of {@code this}. (A prominent example for the use
 * of self-types in the JDK is the abstract {@link Enum} class.) It is imperative to follow the
 * general contract of {@code self()} as laid out below.
 *
 * @param <S> the implementing {@code SelfTyped} class.
 * @author Sebastian Schweikl
 */
public interface SelfTyped<S extends SelfTyped<S>> {

    /**
     * <p>
     * Returns the runtime type of the implementor (a.k.a. "self-type"). This method must only be
     * implemented in concrete, non-abstract subclasses by returning a reference to {@code this},
     * and nothing else. Returning a reference to any other runtime type other than {@code this}
     * breaks the contract.
     * <p>
     * In other words, every concrete subclass {@code Foo} that implements the interface {@code
     * SelfTyped} must implement this method as follows:
     * <pre>{@code
     * public final class Foo implements SelfTyped<Foo> {
     *     @Override
     *     public Foo self() {
     *         return this;
     *     }
     * }
     * }</pre>
     *
     * @return a reference to the self-type
     */
    S self();
}
