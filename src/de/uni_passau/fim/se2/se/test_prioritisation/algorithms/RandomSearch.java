package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.Encoding;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.EncodingGenerator;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.FitnessFunction;
import de.uni_passau.fim.se2.se.test_prioritisation.stopping_conditions.StoppingCondition;


/**
 * Implements a random search space exploration. To this end, a number of solutions are sampled in a
 * random fashion and the best encountered solution is returned.
 *
 * @param <E> the type of encoding
 */
public final class RandomSearch<E extends Encoding<E>> implements SearchAlgorithm<E> {
    private final StoppingCondition stoppingCondition;
    private final EncodingGenerator<E> encodingGenerator;
    private final FitnessFunction<E> fitnessFunction;

    private E bestSolution;
    private double bestFitness;

    public RandomSearch(
            final StoppingCondition stoppingCondition,
            final EncodingGenerator<E> encodingGenerator,
            final FitnessFunction<E> fitnessFunction) {
                
            this.stoppingCondition = stoppingCondition;
            this.encodingGenerator = encodingGenerator;
            this.fitnessFunction = fitnessFunction;
            this.bestSolution = null;
            this.bestFitness = Double.NEGATIVE_INFINITY;
    }

    /**
     * Implements a random search space exploration by generating random solutions until the stopping condition is met
     *
     * @return the best solution found
     */
    @Override
    public E findSolution() {
        stoppingCondition.notifySearchStarted();

        while (!stoppingCondition.searchMustStop()) {
            E candidateSolution = encodingGenerator.get();
            double candidateFitness = fitnessFunction.maximise(candidateSolution);
            stoppingCondition.notifyFitnessEvaluation();
            if (candidateFitness > bestFitness) {
                bestFitness = candidateFitness;
                bestSolution = candidateSolution;
            }
        }
        return bestSolution;
    }
    @Override
    public StoppingCondition getStoppingCondition() {
        return stoppingCondition;
    }
}
