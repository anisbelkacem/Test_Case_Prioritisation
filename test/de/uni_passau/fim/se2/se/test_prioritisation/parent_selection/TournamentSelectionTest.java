package de.uni_passau.fim.se2.se.test_prioritisation.parent_selection;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.fitness_functions.APLC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TournamentSelectionTest {

    private APLC fitnessFunction;
    private Random random;
    private TournamentSelection selection;
    private TournamentSelection selection1;

    @BeforeEach
    void setUp() {
        fitnessFunction = mock(APLC.class);
        random = new Random(50); 
        selection = new TournamentSelection(3, fitnessFunction, random);
        selection1 = new TournamentSelection(fitnessFunction, random);
    }

    @Test
    void testConstructorWithValidArguments() {
        assertNotNull(selection1, "TournamentSelection should be created with valid arguments.");
        assertNotNull(selection, "TournamentSelection should be created with valid arguments.");
    }

    @Test
    void testConstructorWithNullArguments() {
        assertThrows(NullPointerException.class, () -> new TournamentSelection(3, null, random),
                     "Constructor should throw NullPointerException if fitnessFunction is null.");
        assertThrows(NullPointerException.class, () -> new TournamentSelection(3, fitnessFunction, null),
                     "Constructor should throw NullPointerException if Random is null.");
    }

    @Test
    void testConstructorWithInvalidTournamentSize() {
        assertThrows(IllegalArgumentException.class, () -> new TournamentSelection(0, fitnessFunction, random),
                     "Constructor should throw IllegalArgumentException if tournament size is zero.");
        assertThrows(IllegalArgumentException.class, () -> new TournamentSelection(-1, fitnessFunction, random),
                     "Constructor should throw IllegalArgumentException if tournament size is negative.");
    }

    @Test
    void testSelectParentWithValidPopulation() {
        List<TestOrder> population = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TestOrder individual = mock(TestOrder.class);
            when(fitnessFunction.applyAsDouble(individual)).thenReturn((double) i);
            population.add(individual);
        }

        TestOrder selectedParent = selection.selectParent(population);

        assertNotNull(selectedParent, "Selected parent should not be null.");
        assertTrue(population.contains(selectedParent), "Selected parent should be part of the population.");
    }

    @Test
    void testSelectParentWithExactTournamentSize() {
        List<TestOrder> population = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TestOrder individual = mock(TestOrder.class);
            when(fitnessFunction.applyAsDouble(individual)).thenReturn((double) i);
            population.add(individual);
        }

        selection = new TournamentSelection(3, fitnessFunction, random);

        TestOrder selectedParent = selection.selectParent(population);

        assertNotNull(selectedParent, "Selected parent should not be null.");
        assertTrue(population.contains(selectedParent), "Selected parent should be part of the population.");
        assertEquals(2.0, fitnessFunction.applyAsDouble(selectedParent),
                     "Selected parent should have the highest fitness in the population.");
    }

    @Test
    void testSelectParentWithEmptyPopulation() {
        List<TestOrder> population = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> selection.selectParent(population),
                     "Selecting a parent from an empty population should throw IllegalArgumentException.");
    }

    @Test
    void testSelectParentWithNullPopulation() {

        assertThrows(IllegalArgumentException.class, () -> selection.selectParent(null),
                     "Selecting a parent from a null population should throw IllegalArgumentException.");
    }

    @Test
    void testSelectParentWithTournamentSizeLargerThanPopulation() {
        
        List<TestOrder> population = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            population.add(mock(TestOrder.class));
        }

        assertThrows(IllegalArgumentException.class, () -> selection.selectParent(population),
                     "Selecting a parent with a tournament size larger than the population size should throw IllegalArgumentException.");
    }
}
