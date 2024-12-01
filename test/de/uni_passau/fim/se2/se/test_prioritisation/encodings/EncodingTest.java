package de.uni_passau.fim.se2.se.test_prioritisation.encodings;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private Mutation<TestEncoding> Mutation;
    private TestEncoding testEncoding;

    @BeforeEach
    void setUp() {
        Mutation = encoding -> {
            int[] mutatedData = encoding.getData().clone();
            for (int i = 0; i < mutatedData.length; i++) {
                mutatedData[i] = mutatedData[i] + 1;  
            }
            return new TestEncoding(encoding.getMutation(), mutatedData);
        };

        int[] initialData = {1, 2, 3};
        testEncoding = new TestEncoding(Mutation, initialData);
    }
    @Test
    void testConstructorWithValidMutation() {
        assertNotNull(testEncoding.getMutation(), "Mutation should not be null");
    }

    @Test
    void testConstructorWithNullMutation() {
        assertThrows(NullPointerException.class, () -> new TestEncoding(null, new int[]{1, 2, 3}),
                     "Constructor should throw NullPointerException for null mutation");
    }

    @Test
    void testCopyConstructor() {
        int[] initialData = {1, 2, 3};
        TestEncoding originalEncoding = new TestEncoding(Mutation, initialData);
        TestEncoding copiedEncoding = originalEncoding.deepCopy();
        assertNotSame(originalEncoding, copiedEncoding, "Copy should not be the same instance as the original");
        assertSame(originalEncoding.getMutation(), copiedEncoding.getMutation(), 
                "Copy should have the same mutation as the original");

        assertArrayEquals(originalEncoding.getData(), copiedEncoding.getData(),
                        "Copied data should match the original data");
        assertNotSame(originalEncoding.getData(), copiedEncoding.getData(),
                    "Copied data should be independent of the original data");
    }


    @Test
    void testMutate() {
        TestEncoding mutatedEncoding = testEncoding.mutate();
        int[] mutatedData = mutatedEncoding.getData();
        int[] expectedData = {2, 3, 4};  
        assertArrayEquals(expectedData, mutatedData, "Mutate should increment each element by 1");
    }

    @Test
    void testDeepCopy() {
        TestEncoding copiedEncoding = testEncoding.deepCopy();
        assertNotSame(testEncoding, copiedEncoding, "Deep copy should create a new instance");
        assertArrayEquals(testEncoding.getData(), copiedEncoding.getData(),
                          "Deep copy should have the same data");
    }

    @Test
    void testSelfTyped() {
        TestEncoding self = testEncoding.self();
        assertSame(testEncoding, self, "self() should return the same instance");
    }
}
