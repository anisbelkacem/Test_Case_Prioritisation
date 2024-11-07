package de.uni_passau.fim.se2.se.test_prioritisation.utils;

import de.uni_passau.fim.se2.se.test_prioritisation.algorithms.*;
import de.uni_passau.fim.se2.se.test_prioritisation.crossover.Crossover;
import de.uni_passau.fim.se2.se.test_prioritisation.crossover.OrderCrossover;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrderGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.ShiftToBeginningMutation;
import de.uni_passau.fim.se2.se.test_prioritisation.parent_selection.ParentSelection;
import de.uni_passau.fim.se2.se.test_prioritisation.parent_selection.TournamentSelection;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.MaxFitnessEvaluations;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;

import java.util.Random;

/**
 * Utility class for instantiating search algorithms.
 */
public class AlgorithmBuilder {

    /**
     * Builds the specified search {@code algorithm} using the given {@code random} number
     * generator, {@code stoppingCondition} and {@code coverageMatrix}.
     *
     * @param algorithm             the algorithm to build
     * @param maxFitnessEvaluations the maximum number of fitness evaluations to perform
     * @param coverageMatrix        the coverage matrix representing the problem instance
     * @param random                the RNG instance to use
     * @return the instantiated search algorithm
     */
    public static SearchAlgorithm<TestOrder> build(
            final SearchAlgorithmType algorithm,
            int maxFitnessEvaluations,
            final boolean[][] coverageMatrix,
            final Random random) {
        StoppingCondition stoppingCondition = new MaxFitnessEvaluations(maxFitnessEvaluations);
        return switch (algorithm) {
            case RANDOM_SEARCH -> buildRandomSearch(stoppingCondition, coverageMatrix, random);
            case RANDOM_WALK -> buildRandomWalk(stoppingCondition, coverageMatrix, random);
            case SIMULATED_ANNEALING -> buildSimulatedAnnealing(stoppingCondition, coverageMatrix, random);
            case SIMPLE_GENETIC_ALGORITHM -> buildSimpleGeneticAlgorithm(stoppingCondition, coverageMatrix, random);
        };
    }

    /**
     * Returns an implementation of the Random Search algorithm to find a solution for the
     * test case prioritization problem.
     *
     * @param stoppingCondition the stopping condition to use
     * @param coverageMatrix    the coverage matrix representing the problem instance
     * @param random            the RNG instance to use
     * @return the instantiated Random Search algorithm
     */
    private static RandomSearch<TestOrder> buildRandomSearch(
            final StoppingCondition stoppingCondition,
            final boolean[][] coverageMatrix,
            final Random random) {
        final TestOrderGenerator encodingGenerator = buildTestOrderGenerator(coverageMatrix, random);
        final FitnessFunction<TestOrder> fitnessFunction = new APLC(coverageMatrix);
        return new RandomSearch<>(stoppingCondition, encodingGenerator, fitnessFunction);
    }

    /**
     * Returns an implementation of the Random Walk algorithm to find a solution for the
     * test case prioritization problem.
     *
     * @param stoppingCondition the stopping condition to use
     * @param coverageMatrix    the coverage matrix representing the problem instance
     * @param random            the RNG instance to use
     * @return the instantiated Random Walk algorithm
     */
    private static RandomWalk<TestOrder> buildRandomWalk(
            final StoppingCondition stoppingCondition,
            final boolean[][] coverageMatrix,
            final Random random) {
        final TestOrderGenerator encodingGenerator = buildTestOrderGenerator(coverageMatrix, random);
        final FitnessFunction<TestOrder> fitnessFunction = new APLC(coverageMatrix);
        return new RandomWalk<>(stoppingCondition, encodingGenerator, fitnessFunction);

    }

    /**
     * Returns an implementation of the Simulated Annealing algorithm to find a solution for the
     * test case prioritization problem.
     *
     * @param stoppingCondition the stopping condition to use
     * @param coverageMatrix    the coverage matrix representing the problem instance
     * @param random            the RNG instance to use
     * @return the instantiated Simulated Annealing algorithm
     */
    private static SearchAlgorithm<TestOrder> buildSimulatedAnnealing(
            final StoppingCondition stoppingCondition,
            final boolean[][] coverageMatrix,
            final Random random) {
        final TestOrderGenerator encodingGenerator = buildTestOrderGenerator(coverageMatrix, random);
        final FitnessFunction<TestOrder> fitnessFunction = new APLC(coverageMatrix);
        final int numTestCases = coverageMatrix.length;
        int degreesOfFreedom = Utils.degreesOfFreedom(numTestCases);
        return new SimulatedAnnealing<>(stoppingCondition, encodingGenerator, fitnessFunction, degreesOfFreedom, random);
    }

    /**
     * Returns an implementation of the Simple Genetic Algorithm to find a solution for the
     * test case prioritization problem.
     *
     * @param stoppingCondition the stopping condition to use
     * @param coverageMatrix    the coverage matrix representing the problem instance
     * @param random            the RNG instance to use
     * @return the instantiated Simple Genetic Algorithm
     */
    private static SimpleGeneticAlgorithm<TestOrder> buildSimpleGeneticAlgorithm(
            final StoppingCondition stoppingCondition,
            final boolean[][] coverageMatrix,
            final Random random) {
        final TestOrderGenerator encodingGenerator = buildTestOrderGenerator(coverageMatrix, random);
        final APLC fitnessFunction = new APLC(coverageMatrix);
        final Crossover<TestOrder> crossover = new OrderCrossover(random);
        final ParentSelection<TestOrder> parentSelection = new TournamentSelection(fitnessFunction, random);
        return new SimpleGeneticAlgorithm<>(stoppingCondition, encodingGenerator, fitnessFunction, crossover, parentSelection, random);
    }

    /**
     * Builds a test order generator.
     *
     * @param coverageMatrix the coverage matrix representing the problem instance
     * @param random         the RNG instance to use
     * @return the instantiated test order generator
     */
    private static TestOrderGenerator buildTestOrderGenerator(final boolean[][] coverageMatrix, final Random random) {
        final Mutation<TestOrder> mutation = new ShiftToBeginningMutation(random);
        final int numTestCases = coverageMatrix.length;
        return new TestOrderGenerator(random, mutation, numTestCases);
    }

}
