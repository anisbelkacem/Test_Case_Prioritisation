package de.uni_passau.fim.se2.se.test_prioritisation.algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchAlgorithmTypeTest {

    @Test
    void testEnumValues() {
        SearchAlgorithmType[] expectedValues = {
            SearchAlgorithmType.RANDOM_SEARCH,
            SearchAlgorithmType.RANDOM_WALK,
            SearchAlgorithmType.SIMULATED_ANNEALING,
            SearchAlgorithmType.SIMPLE_GENETIC_ALGORITHM
        };
        assertEquals(expectedValues.length, SearchAlgorithmType.values().length, "Enum should have 4 values");
        
        for (SearchAlgorithmType expectedValue : expectedValues) {
            assertTrue(contains(SearchAlgorithmType.values(), expectedValue), 
                       expectedValue + " should be in the enum values");
        }
    }

    private boolean contains(SearchAlgorithmType[] array, SearchAlgorithmType value) {
        for (SearchAlgorithmType item : array) {
            if (item == value) {
                return true;
            }
        }
        return false;
    }
}