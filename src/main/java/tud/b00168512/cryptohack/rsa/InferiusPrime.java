package tud.b00168512.cryptohack.rsa;

import java.math.BigInteger;

public class InferiusPrime {

    public static void main(String[] args) {
        var n = new BigInteger("984994081290620368062168960884976209711107645166770780785733");
        var e = 65537;
        var ciphertext = new BigInteger("948553474947320504624302879933619818331484350431616834086273");

        // USING Python's "factordb 984994081290620368062168960884976209711107645166770780785733" to get p and q from db:
        var p = new BigInteger("848445505077945374527983649411");
        var q = new BigInteger("1160939713152385063689030212503");

        var totient = EulersTotient.computeEulersTotient(p, q);

        // d = e^(-1) mod totient(n)
        var d = BigInteger.valueOf(e).modInverse(totient);

        var res = ciphertext.modPow(d, n);

        System.out.println(new String(res.toByteArray())); // crypto{N33d_b1g_pR1m35}
    }
}
