package de.uni_passau.fim.se2.se.test_prioritisation;

import de.uni_passau.fim.se2.se.test_prioritisation.algorithms.SearchAlgorithm;
import de.uni_passau.fim.se2.se.test_prioritisation.algorithms.SearchAlgorithmType;
import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;
import de.uni_passau.fim.se2.se.test_prioritisation.utils.AlgorithmBuilder;
import de.uni_passau.fim.se2.se.test_prioritisation.utils.CoverageTracker;
import de.uni_passau.fim.se2.se.test_prioritisation.utils.Randomness;
import de.uni_passau.fim.se2.se.test_prioritisation.utils.Utils;
import picocli.CommandLine;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.stream.DoubleStream;

/***
 * Main class for the test prioritisation application that implements the command line interface.
 */
public class Main implements Callable<Integer> {

    @CommandLine.Option(
            names = {"-c", "--class"},
            description = "The name of the class under test.",
            required = true)
    private String className;

    @CommandLine.Option(
            names = {"-p", "--package"},
            description = "The package containing the class under test.",
            defaultValue = "de.uni_passau.fim.se2.se.test_prioritisation.examples")
    private String packageName;

    @CommandLine.Option(
            names = {"-f", "--max-fitness-evaluations"},
            description = "The maximum number of fitness evaluations per repetition.",
            defaultValue = "1000")
    private int maxFitnessEvaluations;

    @CommandLine.Option(
            names = {"-r", "--repetitions"},
            description = "The number of search repetitions to perform.",
            defaultValue = "30")
    private int repetitions;

    @CommandLine.Option(
            names = {"-s", "--seed"},
            description = "Use a fixed RNG seed.")
    public void setSeed(int seed) {
        Randomness.random().setSeed(seed);
    }

    @CommandLine.Parameters(
            paramLabel = "algorithms",
            description = "The search algorithms to use.",
            arity = "1...",
            converter = AlgorithmConverter.class)
    private List<SearchAlgorithmType> algorithms;

    /**
     * The names of the test cases (corresponding to the coverage matrix). That is, for and index
     * {@code i}, {@code testCases[i]} tells the name of the ith test case and
     * {@code coverageMatrix[i]} tells which lines of code are covered by the ith test case.
     */
    private String[] testCases;

    /**
     * The coverage matrix for the analyzed software system.
     */
    private boolean[][] coverageMatrix;


    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    /**
     * Executes the specified search algorithms and prints a summary of the obtained results.
     *
     * @return The exit code of the application
     */
    public Integer call() {
        try {
            setCoverageMatrixAndTestCaseNames();
        } catch (Exception e) {
            System.err.println("Error while initializing coverage matrix and test case names.");
            return 1;
        }

        Map<SearchAlgorithmType, double[]> results = performSearch();
        printSummary(results);
        return 0;
    }

    /**
     * Performs the search, returning a mapping from executed search algorithms to the
     * corresponding results found by the algorithms.
     *
     * @return search results
     */
    private Map<SearchAlgorithmType, double[]> performSearch() {
        final Random random = Randomness.random();
        final Map<SearchAlgorithmType, double[]> results = new LinkedHashMap<>();

        for (final var algorithm : algorithms) {
            System.out.println(" * Executing " + algorithm.name());

            final SearchAlgorithm<?> searchAlgorithm = AlgorithmBuilder.build(algorithm, maxFitnessEvaluations, coverageMatrix, random);
            final double[] solutions = repeatSearch(searchAlgorithm);
            results.put(algorithm, solutions);
        }

        return results;
    }

    /**
     * Repeatedly executes the given search algorithm. The number of repetitions was specified on
     * the command line, or corresponds to the default value if nothing was specified. Returns an
     * array of the resulting APLC values. The array contains one entry for every repetition.
     * Every entry is the best APLC value of its repetition.
     *
     * @param search the search algorithm to run
     * @return an array of APLC values representing the search results over the specified number of repetitions
     */
    private double[] repeatSearch(final SearchAlgorithm<?> search) {
        final var aplcValues = new double[repetitions];
        APLC fitnessFunction = new APLC(coverageMatrix.clone());

        for (int i = 0; i < repetitions; i++) {
            System.out.println("   > Repetition " + i);

            final long start = System.currentTimeMillis();
            final var solution = search.findSolution();
            final long totalTime = System.currentTimeMillis() - start;

            final double aplcValue = fitnessFunction.applyAsDouble((TestOrder) solution);
            aplcValues[i] = aplcValue;

            final String testCaseOrder = Utils.getTestCaseOrder(testCases, (TestOrder) solution);
            System.out.println("      - Ordering: " + testCaseOrder);
            System.out.println("      - APLC: " + aplcValue);
            System.out.printf("      - Time: %fs%n", totalTime / 1000d);
        }

        return aplcValues;
    }

    /**
     * Initializes the coverage matrix and the test case names.
     *
     * @throws Exception if an error occurs while initializing the coverage matrix and the test case names
     */
    private void setCoverageMatrixAndTestCaseNames() throws Exception {
        final String fullyQualifiedClassName = packageName + "." + className;
        CoverageTracker tracker = new CoverageTracker(fullyQualifiedClassName);
        this.coverageMatrix = tracker.getCoverageMatrix();
        this.testCases = tracker.getTestCases();
    }

    /**
     * Prints a summary of the given results to the console.
     *
     * @param results the results to write
     */
    private void printSummary(final Map<SearchAlgorithmType, double[]> results) {
        System.out.println("Summary: " + className);
        for (final var entry : results.entrySet()) {
            final SearchAlgorithmType algorithm = entry.getKey();
            final double[] values = entry.getValue();
            final var stats = DoubleStream.of(values).summaryStatistics();
            System.out.println(" * " + algorithm.name());
            System.out.println("   > min: " + stats.getMin());
            System.out.println("   > avg: " + stats.getAverage());
            System.out.println("   > max: " + stats.getMax());
        }
    }

}


/**
 * Converts supplied cli parameters to the respective {@link SearchAlgorithmType}.
 */
class AlgorithmConverter implements CommandLine.ITypeConverter<SearchAlgorithmType> {
    @Override
    public SearchAlgorithmType convert(String algorithm) {
        return switch (algorithm.toUpperCase()) {
            case "RS" -> SearchAlgorithmType.RANDOM_SEARCH;
            case "RW" -> SearchAlgorithmType.RANDOM_WALK;
            case "SA" -> SearchAlgorithmType.SIMULATED_ANNEALING;
            case "GA" -> SearchAlgorithmType.SIMPLE_GENETIC_ALGORITHM;
            default -> throw new IllegalArgumentException("The algorithm '" + algorithm + "' is not a valid option.");
        };
    }
}