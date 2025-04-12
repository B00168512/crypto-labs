package tud.b00168512.lab13;

import java.math.BigInteger;

public class Modulo {

    public static void main(String[] args) {
        System.out.println("" + 345 % 255);
        System.out.println(modulo(256, 255));
//        System.out.println(calculateByte(254, 2, 255));
    }

    public static int modulo(int value, int modulo) {
        return BigInteger.valueOf(value).mod(BigInteger.valueOf(modulo)).intValue();
    }

    public static int calculateByte(int r1Byte, int moduloResult) {
        if (moduloResult >= r1Byte) {
            return moduloResult - r1Byte;
        } else {
            return 255 + moduloResult - r1Byte;
        }
    }
}
