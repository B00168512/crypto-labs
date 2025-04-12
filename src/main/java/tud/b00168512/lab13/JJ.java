package tud.b00168512.lab13;

public class JJ {

    public static int lfsrStep(int state, int length, int[] taps) {
        int feedbackBit = 0;
        for (int tap : taps) {
            if (((state >> (length - 1 - tap)) & 1) == 1) {
                feedbackBit ^= 1;
            }
        }
        int new_state = (state << 1) & ((1 << length) - 1);
        new_state |= feedbackBit;
        return new_state;
    }

    public static int getOutputByte(int state) {
        return state & 0xFF;
    }

    public static void main(String[] args) {
        String[] keystreamHex = {"e9", "45", "bb", "1c", "4b", "af", "ae", "16"};
        int[] keystream = new int[keystreamHex.length];
        for (int i = 0; i < keystreamHex.length; i++) {
            keystream[i] = Integer.parseInt(keystreamHex[i], 16);
        }

        boolean found = false;
        for (int initX = 0; initX < (1 << 12); initX++) {
            int stateX = initX;
            int[] xOutputs = new int[8];
            for (int i = 0; i < 8; i++) {
                xOutputs[i] = getOutputByte(stateX);
                int feedbackX = (((stateX >> 11) & 1) ^ ((stateX >> 6) & 1) ^ ((stateX >> 1) & 1));
                stateX = (stateX >> 1) | (feedbackX << 11);
            }

            int[] yOutputsRequired = new int[8];
            for (int i = 0; i < 8; i++) {
                yOutputsRequired[i] = (keystream[i] - xOutputs[i] + 255) % 255;
            }

            for (int upper11Bits = 0; upper11Bits < (1 << 11); upper11Bits++) {
                int initY = (upper11Bits << 8) | yOutputsRequired[0];
                int stateY = initY;
                int[] yOutputsGenerated = new int[8];
                for (int i = 0; i < 8; i++) {
                    yOutputsGenerated[i] = getOutputByte(stateY);
                    int feedbackY = (((stateY >> 18) & 1) ^ ((stateY >> 10) & 1) ^ ((stateY >> 4) & 1));
                    stateY = (stateY >> 1) | (feedbackY << 18);
                }

                if (java.util.Arrays.equals(yOutputsGenerated, yOutputsRequired)) {
                    System.out.printf("Found initial state of x: %d (0x%03x)%n", initX, initX);
                    System.out.printf("Found initial state of y: %d (0x%05x)%n", initY, initY);
                    found = true;
                    break;
                }
            }

            if (found) {
                break;
            }
        }

        if (!found) {
            System.out.println("Initial states not found within the search space.");
        }
    }
}
