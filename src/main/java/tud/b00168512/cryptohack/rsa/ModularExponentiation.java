package tud.b00168512.cryptohack.rsa;

import java.math.BigInteger;

public class ModularExponentiation {

    public static void main(String[] args) {
        var res = BigInteger.valueOf(101).pow(17).mod(BigInteger.valueOf(22663));
        System.out.println(res.toString());
    }
}
