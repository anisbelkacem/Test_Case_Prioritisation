package de.uni_passau.fim.se2.se.test_prioritisation.mutations;

import de.uni_passau.fim.se2.se.test_prioritisation.encodings.TestOrder;
import de.uni_passau.fim.se2.se.test_prioritisation.utils.Randomness;

import java.util.Random;

/**
 * A mutation that shifts a test to the beginning of the sequence.
 */
public class ShiftToBeginningMutation implements Mutation<TestOrder> {

    /**
     * The internal source of randomness.
     */
    private final Random random;

    public ShiftToBeginningMutation(final Random random) {
        this.random = random;
    }

    /**
     * Shifts a test to the beginning of the sequence.
     *
     * @param encoding the test order to be mutated
     * @return the mutated test order
     */
    @Override
public TestOrder apply(TestOrder encoding) {
    if(encoding == null)
    {
        throw new NullPointerException("encoding is null");
    }
    int[] positions = encoding.getPositions();

    int randomIndex = random.nextInt(positions.length);

    int[] mutatedPositions = new int[positions.length];
    
    mutatedPositions[0] = positions[randomIndex];

    int currentIndex = 1;
    for (int i = 0; i < positions.length; i++) {
        if (i != randomIndex) {
            mutatedPositions[currentIndex++] = positions[i];
        }
    }

    return new TestOrder(encoding.getMutation(), mutatedPositions);
}

}
