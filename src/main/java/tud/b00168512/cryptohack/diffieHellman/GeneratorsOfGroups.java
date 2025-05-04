package tud.b00168512.cryptohack.diffieHellman;

import java.math.BigInteger;

public class GeneratorsOfGroups {

    public static void main(String[] args) {
        int p = 28151;
        for (int i = 1; i < p; i++) {
            boolean isPrimitiveElement = true;
            for (int pow = 1; pow < p - 1; pow++) {
                var modPow = BigInteger.valueOf(i).modPow(BigInteger.valueOf(pow), BigInteger.valueOf(p));
                if (modPow.equals(BigInteger.ONE)) {
                    isPrimitiveElement = false;
                    break;
                }
            }
            if (isPrimitiveElement) {
                System.out.println("Result: " + i);
                break;
            }
        }
    }
}
