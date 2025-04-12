package tud.b00168512.lab13;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;

public class LFSR {

    private final int initialState;
    private final int[] taps;
    private final int length;

    private int state;
    private BitSet stateBs;

    public LFSR(int initialState, int[] taps, int length) {
        this.initialState = initialState;
        this.state = initialState;
        this.taps = taps;
        this.length = length;
        this.stateBs = BitSet.valueOf(new long[]{initialState});

    }

    public int initialRegister() {
        return initialState;
    }

    public void reset() {
        this.state = initialState;
    }

    public int state() {
        return this.state;
    }

    public int getNext() {
//        var byteValue = 0;
        var bs = new BitSet(8);
        for (var step = 0; step < 8; step++) {
            var outputBit = nextBit();
//            var outputBit = nextBit2(taps[0], taps[1]);
            if (outputBit == 1) {
                bs.set(step);
            }
//            byteValue <<= 1;
//            byteValue |= outputBit;
        }
//        return (byteValue & 0xFF);
        var lArr = bs.toLongArray();
        if (lArr.length == 0) {
            var bArr = bs.toByteArray();
            if (bArr.length == 0) {
                return 0;
            }
        }
        return (int) bs.toLongArray()[0];
    }

    public int nextBit() {
        int leavingBit = (state >> (length - 1)) & 1;
        int feedbackBit = leavingBit;
        for (int tap : taps) {
            if (((state >> tap) & 1) == 1) {
                feedbackBit ^= 1;
            }
        }
        state = (state << 1) & ((1 << length) - 1);
        state |= feedbackBit;
        return feedbackBit;
    }

    private int nextBitOld() {
        // Get the most significant bit (leaving bit)
        int leavingBit = (state >> (length - 1)) & 1;

        // Calculate the feedback bit by XORing the leaving bit with the bits at the tap positions
        int feedbackBit = leavingBit;
        for (int tap : taps) {
            int tapBit = (state >> tap) & 1;
            feedbackBit ^= tapBit;
        }

        // Shift the seed to the left by one position
        state <<= 1;

        // Set the least significant bit to the feedback bit
        state |= feedbackBit;

        // Mask the seed to ensure it does not exceed the specified number of bits
        state &= ((1 << length) - 1);

        return feedbackBit;
    }

    private int nextBit2(int tap1, int tap2) {
        int leavingBit = (state >> (length - 1)) & 1;
        // Calculate the feedback bit by XORing the bits at the tap positions
        int bit1 = (state >> tap1) & 1;
        int bit2 = (state >> tap2) & 1;
        int feedbackBit = leavingBit ^ bit1 ^ bit2;

        // Shift the state to the right by one bit
        state >>= 1;

        // Set the least significant bit (LSB) to the feedback bit
        state |= (feedbackBit << (length - 1)); // For left shift

        return leavingBit;
    }

    private int nextBit4() {
        var leavingBit = stateBs.get(0);

        var feedbackBit = leavingBit ^ stateBs.get(taps[0]) ^ stateBs.get(taps[1]);

        stateBs = stateBs.get(1, stateBs.length() - 1);

        if (feedbackBit) {
            stateBs.set(0);
        }

        return leavingBit ? 1 : 0;
    }

}
