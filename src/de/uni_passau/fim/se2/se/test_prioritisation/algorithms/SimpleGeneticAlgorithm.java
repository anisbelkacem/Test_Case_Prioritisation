package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.crossover.Crossover;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.parent_selection.ParentSelection;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class SimpleGeneticAlgorithm<E extends Encoding<E>> implements SearchAlgorithm<E> {

    private final StoppingCondition stoppingCondition;
    private final EncodingGenerator<E> encodingGenerator;
    private final FitnessFunction<E> fitnessFunction;
    private final Crossover<E> crossover;
    private final ParentSelection<E> parentSelection;
    private final Random random;
    private static final int POPULATION_SIZE = 100;
    private static final double MUTATION_RATE = 0.1;

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
                this.stoppingCondition = stoppingCondition;
                this.encodingGenerator = encodingGenerator;
                this.fitnessFunction = fitnessFunction;
                this.crossover = crossover;
                this.parentSelection = parentSelection;
                this.random = random;
    }

    /**
     * Runs the genetic algorithm to find a solution to the given problem.
     *
     * @return the best individual found by the genetic algorithm
     */
    @Override
    public E findSolution() {
        stoppingCondition.notifySearchStarted();
        List<E> population = initializePopulation();

        E bestSolution = null;
        double bestFitness = Double.NEGATIVE_INFINITY;
    
        while (!stoppingCondition.searchMustStop()) {
            List<Double> fitnessValues = evaluatePopulationFitness(population);
            for (int i = 0; i < fitnessValues.size(); i++) {
                if (fitnessValues.get(i) > bestFitness) {
                    bestFitness = fitnessValues.get(i);
                    bestSolution = population.get(i);
                }
            }
            if (stoppingCondition.searchMustStop()) break;
    
            List<E> newPopulation = generateNewPopulation(population);
            population = newPopulation;
        }
    
        return bestSolution;
    }
    
    private List<E> initializePopulation() {
        List<E> population = new ArrayList<>(POPULATION_SIZE);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(encodingGenerator.get());
            stoppingCondition.notifyFitnessEvaluation();
            if (stoppingCondition.searchMustStop()) return population;
        }
        return population;
    }
    
    private List<Double> evaluatePopulationFitness(List<E> population) {
        List<Double> fitnessValues = new ArrayList<>(population.size());
        for (E individual : population) {
            double fitness = fitnessFunction.applyAsDouble(individual);
            fitnessValues.add(fitness);
            stoppingCondition.notifyFitnessEvaluation();
            if (stoppingCondition.searchMustStop()) break;
        }
        return fitnessValues;
    }
    
    private List<E> generateNewPopulation(List<E> population) {
        List<E> newPopulation = new ArrayList<>(POPULATION_SIZE);
        while (newPopulation.size() < POPULATION_SIZE) {
            E parent1 = parentSelection.selectParent(population);
            E parent2 = parentSelection.selectParent(population);
            E offspring = crossover.apply(parent1, parent2);
    
            if (random.nextDouble() < MUTATION_RATE) {
                offspring = offspring.getMutation().apply(offspring);
            }
    
            newPopulation.add(offspring);
            stoppingCondition.notifyFitnessEvaluation();
    
            if (stoppingCondition.searchMustStop()) break;
        }
        return newPopulation;
    }
    
    
    @Override
    public StoppingCondition getStoppingCondition() {
        return stoppingCondition;
    }
}
