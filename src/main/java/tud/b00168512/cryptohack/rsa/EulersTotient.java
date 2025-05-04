package tud.b00168512.cryptohack.rsa;

import java.math.BigInteger;

public class EulersTotient {

    private static final BigInteger P = new BigInteger("857504083339712752489993810777");
    private static final BigInteger Q = new BigInteger("1029224947942998075080348647219");

    public static void main(String[] args) {
        var res = computeEulersTotient(P, Q);
        System.out.println(res);
    }

    // Compute totient(n) = (p - 1) * (q - 1)
    public static BigInteger computeEulersTotient(BigInteger p, BigInteger q) {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

}
