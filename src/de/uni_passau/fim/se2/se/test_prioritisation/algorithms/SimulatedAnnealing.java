package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;

import java.util.Random;


/**
 * Implements the Simulated Annealing algorithm for test order prioritisation based on
 * -----------------------------------------------------------------------------------------
 * Flow chart of the algorithm:
 * Bastien Chopard, Marco Tomassini, "An Introduction to Metaheuristics for Optimization",
 * (Springer), Ch. 4.3, Page 63
 * -----------------------------------------------------------------------------------------
 * Note we've applied a few modifications to add elitism.
 *
 * @param <E> the type of encoding
 */
public final class SimulatedAnnealing<E extends Encoding<E>> implements SearchAlgorithm<E> {

    private final StoppingCondition stoppingCondition;
    private final EncodingGenerator<E> encodingGenerator;
    private final FitnessFunction<E> energy;
    private final int degreesOfFreedom;
    private final Random random;

    private double temperature;
    private final double coolingRate = 0.97;  // Set a default cooling rate
    private final double initialTemperature = 1000;  // Set a default initial temperature
    /**
     * Constructs a new simulated annealing algorithm.
     *
     * @param stoppingCondition the stopping condition to use
     * @param encodingGenerator the encoding generator to use
     * @param energy            the energy fitness function to use
     * @param degreesOfFreedom  the number of degrees of freedom of the problem, i.e. the number of variables that define a solution
     * @param random            the random number generator to use
     */
    public SimulatedAnnealing(
            final StoppingCondition stoppingCondition,
            final EncodingGenerator<E> encodingGenerator,
            final FitnessFunction<E> energy,
            final int degreesOfFreedom,
            final Random random) {
                this.stoppingCondition = stoppingCondition;
                this.encodingGenerator = encodingGenerator;
                this.energy = energy;
                this.degreesOfFreedom = degreesOfFreedom;
                this.random = random;
    }


    /**
     * Performs the Simulated Annealing algorithm to search for an optimal solution of the encoded problem.
     * Since Simulated Annealing is designed as a minimisation algorithm, optimal solutions are characterized by a minimal energy value.
     */
    @Override
    public E findSolution() {
        notifySearchStarted();
        temperature = initialTemperature;
        E currentSolution = encodingGenerator.get(); 
        double currentFitness = energy.maximise(currentSolution); 
        E bestSolution = currentSolution; 
        double bestFitness = currentFitness;
        while (!searchMustStop()) {
            E newSolution = currentSolution.deepCopy();
            newSolution = newSolution.mutate(); 
            double newFitness = energy.maximise(newSolution); 
            notifyFitnessEvaluation();
            if (newFitness > currentFitness) {
                
                currentSolution = newSolution;
                currentFitness = newFitness;
            } else {
                double acceptanceProbability = Math.exp((newFitness - currentFitness) / temperature);
                if (random.nextDouble() < acceptanceProbability) {
                    currentSolution = newSolution;
                    currentFitness = newFitness;
                }
            }
            if (currentFitness > bestFitness) {
                bestSolution = currentSolution;
                bestFitness = currentFitness;
            }
            temperature *= coolingRate;
        }
        return bestSolution;
    }


    @Override
    public StoppingCondition getStoppingCondition() {
        return stoppingCondition;
    }
}
