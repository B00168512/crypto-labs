package tud.b00168512.cryptohack.math;

import java.math.BigInteger;
import java.util.Random;

public class TonelliShanks {

    public static void main(String[] args) {
        BigInteger a = new BigInteger("8479994658316772151941616510097127087554541274812435112009425778595495359700244470400642403747058566807127814165396640215844192327900454116257979487432016769329970767046735091249898678088061634796559556704959846424131820416048436501387617211770124292793308079214153179977624440438616958575058361193975686620046439877308339989295604537867493683872778843921771307305602776398786978353866231661453376056771972069776398999013769588936194859344941268223184197231368887060609212875507518936172060702209557124430477137421847130682601666968691651447236917018634902407704797328509461854842432015009878011354022108661461024768");
        BigInteger p = new BigInteger("30531851861994333252675935111487950694414332763909083514133769861350960895076504687261369815735742549428789138300843082086550059082835141454526618160634109969195486322015775943030060449557090064811940139431735209185996454739163555910726493597222646855506445602953689527405362207926990442391705014604777038685880527537489845359101552442292804398472642356609304810680731556542002301547846635101455995732584071355903010856718680732337369128498655255277003643669031694516851390505923416710601212618443109844041514942401969629158975457079026906304328749039997262960301209158175920051890620947063936347307238412281568760161");
        BigInteger sqrt = tonelliShanks(a, p);
        System.out.println("Square root of " + a + " mod " + p + ": " + sqrt);

        BigInteger otherSqrt = p.subtract(sqrt).mod(p);
        BigInteger smallerRoot = sqrt.compareTo(otherSqrt) < 0 ? sqrt : otherSqrt;
        System.out.println("Smaller square root: " + smallerRoot);
    }

    private static BigInteger tonelliShanks(BigInteger a, BigInteger p) {
        if (p.compareTo(BigInteger.TWO) <= 0) {
            throw new IllegalArgumentException("p must be a prime greater than 2");
        }
        if (a.compareTo(BigInteger.ZERO) == 0) {
            return BigInteger.ZERO;
        }
        if (!legendreSymbol(a, p).equals(BigInteger.ONE)) {
            throw new ArithmeticException("a is not a quadratic residue modulo p");
        }

        BigInteger pMinus1 = p.subtract(BigInteger.ONE);
        BigInteger s = BigInteger.ZERO;
        BigInteger t = pMinus1;

        while (t.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            t = t.divide(BigInteger.TWO);
            s = s.add(BigInteger.ONE);
        }

        if (s.equals(BigInteger.ONE)) {
            return a.modPow(t.add(BigInteger.ONE).divide(BigInteger.TWO), p);
        }

        BigInteger n = findNonResidue(p);
        BigInteger R = n.modPow(t, p);
        BigInteger x = a.modPow(t.add(BigInteger.ONE).divide(BigInteger.TWO), p);
        BigInteger y = a.modPow(t, p);
        BigInteger m = s;

        while (!y.equals(BigInteger.ONE)) {
            BigInteger i = BigInteger.ONE;
            BigInteger powerOf2 = BigInteger.TWO;
            while (!y.modPow(powerOf2, p).equals(BigInteger.ONE)) {
                powerOf2 = powerOf2.multiply(BigInteger.TWO);
                i = i.add(BigInteger.ONE);
            }

            BigInteger b = R.modPow(BigInteger.TWO.pow(m.intValue() - i.intValue() - 1), p);
            x = x.multiply(b).mod(p);
            y = y.multiply(b.modPow(BigInteger.TWO, p)).mod(p);
            R = b.modPow(BigInteger.TWO, p);
            m = i;
        }

        return x;
    }

    private static BigInteger legendreSymbol(BigInteger a, BigInteger p) {
        BigInteger result = a.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.TWO), p);
        if (result.equals(p.subtract(BigInteger.ONE))) {
            return BigInteger.valueOf(-1);
        }
        return result;
    }

    private static BigInteger findNonResidue(BigInteger p) {

        Random rand = new Random();
        BigInteger a;
        while (true) {
            a = new BigInteger(p.bitLength(), rand);
            if (a.compareTo(BigInteger.ONE) > 0 && a.compareTo(p) < 0 && legendreSymbol(a, p).equals(BigInteger.valueOf(-1))) {
                return a;
            }
        }
    }
}
