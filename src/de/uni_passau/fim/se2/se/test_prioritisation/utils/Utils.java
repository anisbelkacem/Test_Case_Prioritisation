package de.uni_passau.fim.se2.se.test_prioritisation.utils;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Utils {

    /**
     * Tells whether the coverage matrix is rectangular.
     *
     * @return {@code true} if rectangular, {@code false} otherwise
     */
    public static boolean isRectangularMatrix(boolean[][] coverageMatrix) {
        boolean rectangular = true;
        for (int i = 0; rectangular && i < coverageMatrix.length - 1; ) {
            // All rows in the matrix must have the same length.
            rectangular = coverageMatrix[i++].length == coverageMatrix[i].length;
        }
        return rectangular;
    }

    /**
     * Returns the order of the test cases as a string.
     *
     * @param testCases the test cases to order
     * @param solution  the solution to use
     * @return the order of the test cases as a string
     */
    public static String getTestCaseOrder(final String[] testCases, final TestOrder solution) {
        final int[] ordering = solution.getPositions().clone();
        assert testCases.length == ordering.length;
        final String[] testCaseOrder = new String[testCases.length];
        for (int i = 0; i < ordering.length; i++) {
            testCaseOrder[i] = testCases[ordering[i]];
        }
        return Arrays.toString(testCaseOrder);
    }

    /**
     * Returns the number of degrees of freedom of the test case prioritization problem with regard
     * to the given number of test cases that need to be prioritized.
     *
     * @param testCases the number of test cases to prioritize
     * @return degrees of freedom
     */
    public static int degreesOfFreedom(final int testCases) {
        throw new UnsupportedOperationException("Implement me");
    }

    /**
     * Parses a coverage matrix from a string.
     *
     * @param matrixFile the string representation of the coverage matrix
     * @return the parsed coverage matrix
     * @throws IOException if the supplied file could not be read
     */
    public static boolean[][] parseCoverageMatrix(File matrixFile) throws IOException {
        String matrix = Files.readString(matrixFile.toPath()).replace("\n", " ");

        // Remove outer brackets
        matrix = matrix.substring(1, matrix.length() - 1);

        // Split rows by "], ["
        String[] rows = matrix.split("], \\[");

        // Initialize 2D boolean array
        boolean[][] parsedMatrix = new boolean[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            // Remove any remaining brackets and split by comma
            String[] values = rows[i].replace("[", "").replace("]", "").split(", ");
            parsedMatrix[i] = new boolean[values.length];
            for (int j = 0; j < values.length; j++) {
                // Parse "true" or "false" as boolean
                parsedMatrix[i][j] = Boolean.parseBoolean(values[j]);
            }
        }

        return parsedMatrix;
    }
}
