package de.uni_passau.fim.se2.se.test_prioritisation.parent_selection;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;

import java.util.*;

public class TournamentSelection implements ParentSelection<TestOrder> {

    /**
     * A common default value for the size of the tournament.
     */
    private final static int DEFAULT_TOURNAMENT_SIZE = 5;

    /**
     * Creates a new tournament selection operator.
     *
     * @param tournamentSize  the size of the tournament
     * @param fitnessFunction the fitness function used to rank the test orders
     * @throws NullPointerException if any of the arguments is {@code null}
     */
    public TournamentSelection(int tournamentSize, APLC fitnessFunction, Random random) {
        throw new UnsupportedOperationException("Implement me");
    }

    /**
     * Creates a new tournament selection operator with a default tournament size.
     *
     * @param fitnessFunction the fitness function used to rank the test orders
     * @throws NullPointerException if any of the arguments is {@code null}
     */
    public TournamentSelection(APLC fitnessFunction, Random random) {
        this(DEFAULT_TOURNAMENT_SIZE, fitnessFunction, random);
    }

    /**
     * Selects a single parent from a population to be evolved in the current generation of an evolutionary algorithm
     * using the tournament selection strategy.
     *
     * @param population the population from which to select parents
     * @return the selected parent
     */
    @Override
    public TestOrder selectParent(List<TestOrder> population) {
        throw new UnsupportedOperationException("Implement me");
    }
}
