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
        notifySearchStarted();
        int minSize = 50;
        int maxSize = 200; 
        int populationSize = random.nextInt(maxSize - minSize + 1) + minSize;
        List<E> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(encodingGenerator.get());
        }
        while (!searchMustStop()) {
            E parent1 = parentSelection.selectParent(population);
            E parent2 = parentSelection.selectParent(population);
            E offspring = crossover.apply(parent1, parent2);
            double offspringFitness = fitnessFunction.applyAsDouble(offspring);
            notifyFitnessEvaluation();
    
            E worstIndividual = population.stream()
                    .min(Comparator.comparing(fitnessFunction::applyAsDouble))
                    .orElseThrow();
    
            double worstFitness = fitnessFunction.applyAsDouble(worstIndividual);
            if (offspringFitness > worstFitness) {
                population.remove(worstIndividual);
                population.add(offspring);
            }
        }
        return population.stream()
                .max(Comparator.comparing(fitnessFunction::applyAsDouble))
                .orElseThrow();
    }
    
    @Override
    public StoppingCondition getStoppingCondition() {
        return stoppingCondition;
    }
}
