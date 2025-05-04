package tud.b00168512.cryptohack.diffieHellman;

import java.math.BigInteger;

public class WorkingWithFields {

    public static void main(String[] args) {
        System.out.println(BigInteger.valueOf(209).modPow(BigInteger.valueOf(-1), BigInteger.valueOf(991)));
    }
}
