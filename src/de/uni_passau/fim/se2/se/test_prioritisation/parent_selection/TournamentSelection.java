package de.uni_passau.fim.se2.se.test_prioritisation.parent_selection;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;

import java.util.*;

public class TournamentSelection implements ParentSelection<TestOrder> {

    /**
     * A common default value for the size of the tournament.
     */
    private static final int DEFAULT_TOURNAMENT_SIZE = 5;

    private final int tournamentSize;
    private final APLC fitnessFunction;
    private final Random random;

    /**
     * Creates a new tournament selection operator.
     *
     * @param tournamentSize  the size of the tournament
     * @param fitnessFunction the fitness function used to rank the test orders
     * @throws NullPointerException if any of the arguments is {@code null}
     */
    public TournamentSelection(int tournamentSize, APLC fitnessFunction, Random random) {
        if (fitnessFunction == null || random == null) {
            throw new NullPointerException("Fitness function and Random cannot be null");
        }
        if (tournamentSize <= 0) {
            throw new IllegalArgumentException("Tournament size must be greater than 0");
        }
        this.tournamentSize = tournamentSize;
        this.fitnessFunction = fitnessFunction;
        this.random = random;
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
        if (population == null || population.isEmpty()) {
            throw new IllegalArgumentException("Population cannot be null or empty");
        }
        if (tournamentSize == population.size()) {
            return population.stream()
                    .max(Comparator.comparing(fitnessFunction::applyAsDouble))
                    .orElseThrow(() -> new IllegalStateException("Population is non-empty but no maximum found"));
        }
        if (tournamentSize > population.size()) {
            throw new IllegalArgumentException("Tournament size must be smaller than or equal to the population size");
        }
        List<TestOrder> tournament = new ArrayList<>();
        for (int i = 0; i < tournamentSize; i++) {
            TestOrder randomIndividual = population.get(random.nextInt(population.size()));
            tournament.add(randomIndividual);
        }

        TestOrder bestIndividual = null;
        double bestFitness = Double.NEGATIVE_INFINITY;

        for (TestOrder individual : tournament) {
            double fitness = fitnessFunction.applyAsDouble(individual);
            if (fitness > bestFitness) {
                bestFitness = fitness;
                bestIndividual = individual;
            }
        }

        return bestIndividual;
    }

}
