package tud.b00168512.cryptohack.general;

import java.math.BigInteger;

public class Tests {

    public static void main(String[] args) {
        var k = "kettle";
        System.out.println(k.getBytes().length);

        var tstr = new BigInteger("kettle".getBytes()).toString(2);


        System.out.println("foxtrotanteatercastle".getBytes().length);

        System.out.println(new BigInteger("foxtrotanteatercastle".getBytes()).toString(16).length());
    }
}
