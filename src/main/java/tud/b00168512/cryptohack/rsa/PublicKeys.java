package tud.b00168512.cryptohack.rsa;

import java.math.BigInteger;

public class PublicKeys {

    public static void main(String[] args) {
        var res = BigInteger.valueOf(12).pow(65537).mod(BigInteger.valueOf(17).multiply(BigInteger.valueOf(23)));
        System.out.println(res);
    }
}
