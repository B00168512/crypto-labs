package tud.b00168512.cryptohack.symmetric;

//"crypto{inmatrix}"
public class BytesToMatrix {

    public static void main(String[] args) {
        char[][] matrix = {
                {99, 114, 121, 112},
                {116, 111, 123, 105},
                {110, 109, 97, 116},
                {114, 105, 120, 125}
        };
        System.out.println(matrix2Bytes(matrix));
    }

    private static String matrix2Bytes(char[][] matrix) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                text.append(matrix[i][j]);
            }
        }
        return text.toString();
    }

}
