package de.uni_passau.fim.se2.se.test_prioritisation.encodings;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.uni_passau.fim.se2.se.test_prioritisation.mutations.Mutation;

public class EncodingTest {

    public static class TestEncoding extends Encoding<TestEncoding> {
        private final int[] data;

        public TestEncoding(Mutation<TestEncoding> mutation, int[] data) {
            super(mutation);
            this.data = data.clone();
        }

        public TestEncoding(Encoding<TestEncoding> other, int[] data) {
            super(other); // Calls the copy constructor
            this.data = data.clone();
        }

        public int[] getData() {
            return data.clone();
        }

        @Override
        public TestEncoding deepCopy() {
            return new TestEncoding(getMutation(), data);
        }

        @Override
        public TestEncoding self() {
            return this;
        }
    }

    private Mutation<TestEncoding> mutation;
    private TestEncoding testEncoding;

    @BeforeEach
    void setUp() {
        mutation = encoding -> {
            int[] mutatedData = encoding.getData().clone();
            for (int i = 0; i < mutatedData.length; i++) {
                mutatedData[i] += 1;  // Increment each element
            }
            return new TestEncoding(encoding.getMutation(), mutatedData);
        };

        testEncoding = new TestEncoding(mutation, new int[]{1, 2, 3});
    }

    @Test
    void testConstructorWithValidMutation() {
        assertNotNull(testEncoding.getMutation(), "Mutation should not be null");
    }

    @Test
    void testCopyConstructorEncodingOther() {
        TestEncoding copiedEncoding = new TestEncoding(testEncoding, testEncoding.getData());
        assertNotSame(testEncoding, copiedEncoding, "Copy should not be the same instance as the original");
        assertSame(testEncoding.getMutation(), copiedEncoding.getMutation(), 
            "Copy should have the same mutation as the original");
        assertArrayEquals(testEncoding.getData(), copiedEncoding.getData(), 
            "Copied data should match the original data");
        assertNotSame(testEncoding.getData(), copiedEncoding.getData(), 
            "Copied data should be independent of the original data");
    }

    @Test
    void testMutate() {
        TestEncoding mutatedEncoding = testEncoding.mutate();
        int[] mutatedData = mutatedEncoding.getData();
        assertArrayEquals(new int[]{2, 3, 4}, mutatedData, "Mutate should increment each element by 1");
    }

    @Test
    void testMutateOriginalUnchanged() {
        int[] originalData = testEncoding.getData();
        TestEncoding mutatedEncoding = testEncoding.mutate();
        assertNotSame(testEncoding, mutatedEncoding, "Mutate should create a new instance");
        assertArrayEquals(new int[]{1, 2, 3}, originalData, "Original data should remain unchanged after mutation");
    }

    @Test
    void testEmptyData() {
        TestEncoding emptyEncoding = new TestEncoding(mutation, new int[]{});
        TestEncoding mutatedEncoding = emptyEncoding.mutate();
        assertArrayEquals(new int[]{}, mutatedEncoding.getData(), "Mutate on empty data should return empty data");
    }

    @Test
    void testDeepCopy() {
        TestEncoding copiedEncoding = testEncoding.deepCopy();
        assertNotSame(testEncoding, copiedEncoding, "Deep copy should create a new instance");
        assertArrayEquals(testEncoding.getData(), copiedEncoding.getData(), 
            "Deep copy should have the same data");
    }

    @Test
    void testDeepCopyIndependence() {
        TestEncoding copiedEncoding = testEncoding.deepCopy();
        copiedEncoding.getData()[0] = 42;  // Modify the copy's data
        assertArrayEquals(new int[]{1, 2, 3}, testEncoding.getData(), 
            "Original data should not be affected by modifying the copy");
    }

    @Test
    void testSelfTyped() {
        TestEncoding self = testEncoding.self();
        assertSame(testEncoding, self, "self() should return the same instance");
    }
}
