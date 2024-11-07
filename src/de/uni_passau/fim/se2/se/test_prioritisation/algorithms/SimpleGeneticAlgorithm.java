package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.crossover.Crossover;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.parent_selection.ParentSelection;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;

import java.util.Random;

public final class SimpleGeneticAlgorithm<E extends Encoding<E>> implements SearchAlgorithm<E> {

    /**
     * Creates a new simple genetic algorithm with the given components.
     *
     * @param stoppingCondition the stopping condition to be used by the genetic algorithm
     * @param encodingGenerator the encoding generator used to create the initial population
     * @param fitnessFunction   the fitness function used to evaluate the quality of the individuals in the population
     * @param crossover         the crossover operator used to create offspring from parents
     * @param parentSelection   the parent selection operator used to select parents for the next generation
     * @param random            the source of randomness for this algorithm
     */
    public SimpleGeneticAlgorithm(
            final StoppingCondition stoppingCondition,
            final EncodingGenerator<E> encodingGenerator,
            final FitnessFunction<E> fitnessFunction,
            final Crossover<E> crossover,
            final ParentSelection<E> parentSelection,
            final Random random) {
        throw new UnsupportedOperationException("Implement me");
    }

    /**
     * Runs the genetic algorithm to find a solution to the given problem.
     *
     * @return the best individual found by the genetic algorithm
     */
    @Override
    public E findSolution() {
        throw new UnsupportedOperationException("Implement me");
    }

    @Override
    public StoppingCondition getStoppingCondition() {
        throw new UnsupportedOperationException("Implement me");
    }
}
