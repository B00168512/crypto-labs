package tud.b00168512.cryptohack.symmetric;

// crypto{r0undk3y}
public class RoundKey {

    private static final int[][] state = {
            {206, 243, 61, 34},
            {171, 11, 93, 31},
            {16, 200, 91, 108},
            {150, 3, 194, 51}
    };

    private static final int[][] roundKey = {
            {173,129,68,82},
            {223, 100, 38, 109},
            {32, 189, 53, 8},
            {253, 48, 187, 78}
    };

    public static void main(String[] args) {
        System.out.println(matrixToBytes(addRoundKey()));
    }

    private static int[][] addRoundKey() {
        int[][] result = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = state[i][j] ^ roundKey[i][j];
            }
        }
        return result;
    }

    private static String matrixToBytes(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sb.append((char) matrix[i][j]);
            }
        }
        return sb.toString();
    }
}
