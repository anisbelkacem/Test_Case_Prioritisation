package de.uni_passau.fim.se2.se.test_prioritisation.utils;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/**
 * Allows iteration over a collection, grouping its elements into blocks of size 2 by passing a
 * "sliding window" over them.
 *
 * @author Sebastian Schweikl
 */
public class SlidingDoublePair implements Spliterator<double[]> {

    private static final int characteristics = SIZED;

    private final PrimitiveIterator.OfDouble iterator;
    private final int size;

    private double fst;
    private boolean buffered = false;

    public SlidingDoublePair(final double[] source) {
        Objects.requireNonNull(source);

        this.iterator = new PrimitiveIterator.OfDouble() {
            private int i = 0;

            @Override
            public double nextDouble() {
                return source[i++];
            }

            @Override
            public boolean hasNext() {
                return i < source.length;
            }
        };

        final int size = source.length;
        this.size = size < 2 ? 0 : size - 1;
    }

    public static Stream<double[]> of(final double[] source) {
        return StreamSupport.stream(new SlidingDoublePair(source), false);
    }

    @Override
    public boolean tryAdvance(final Consumer<? super double[]> action) {
        // In the very beginning, the pipeline must be filled with two elements before the window
        // can be slided.
        if (!buffered) {
            if (iterator.hasNext()) {
                fst = iterator.next();
                buffered = true;
            } else {
                return false;
            }
        }

        final double snd;
        if (iterator.hasNext()) {
            snd = iterator.next();
        } else {
            return false;
        }

        action.accept(new double[]{fst, snd});
        fst = snd;

        return true;
    }

    @Override
    public Spliterator<double[]> trySplit() {
        return null; // not supported
    }

    @Override
    public long estimateSize() {
        return size;
    }

    @Override
    public int characteristics() {
        return characteristics;
    }
}
