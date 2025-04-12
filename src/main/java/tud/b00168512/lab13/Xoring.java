package tud.b00168512.lab13;

import java.math.BigInteger;

public class Xoring {

    public static void main(String[] args) {
        System.out.println(xor("89", "60", 2));
//        System.out.println(xor("", "", 2));
    }

    public static String xor(String hex1, String hex2, int returnRadix) {
        return new BigInteger(hex1, 16).xor(new BigInteger(hex2, 16)).toString(returnRadix);
    }

    public static String xor(byte b1, byte b2, int returnRadix) {
        var r = b1 ^ b2;
        return BigInteger.valueOf(r).toString(returnRadix);
    }
}
