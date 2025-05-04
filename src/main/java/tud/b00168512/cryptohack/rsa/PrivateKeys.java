package tud.b00168512.cryptohack.rsa;

import java.math.BigInteger;

public class PrivateKeys {

    static final BigInteger PRIVATE_KEY = new BigInteger("121832886702415731577073962957377780195510499965398469843281");

    public static void main(String[] args) {
        var e = BigInteger.valueOf(65537);
        var p = new BigInteger("857504083339712752489993810777");
        var q = new BigInteger("1029224947942998075080348647219");
        // Compute totient(n) = (p - 1) * (q - 1)
        var totient = EulersTotient.computeEulersTotient(p, q);

        // d = e^(-1) mod phi(n)
        var d = e.modInverse(totient);

        System.out.println(d);
        System.out.println(d.equals(PRIVATE_KEY));
    }
}
