package tud.b00168512.cryptohack.rsa;

import java.math.BigInteger;

import org.apache.commons.math3.util.ArithmeticUtils;

/**
 * DIDN'T WORK
 * Prime factors of n are:
 * p = 3
 * q = 170047919578503008510293400217732153510884382
 */
public class Factoring {

    public static void main(String[] args) {
        // The 150-bit number to be factored
        BigInteger n = new BigInteger("510143758735509025530880200653196460532653147");

        // Factor the number into two prime factors
        BigInteger[] factors = factorize(n);

        if (factors != null) {
            System.out.println("Prime factors of n are:");
            System.out.println("p = " + factors[0]);
            System.out.println("q = " + factors[1]);
        } else {
            System.out.println("Could not factorize the number.");
        }
    }

    // Method to factorize the number n
    public static BigInteger[] factorize(BigInteger n) {
        // Pollard's Rho algorithm to find one non-trivial factor
        BigInteger factor1 = pollardsRho(n);

        if (factor1 != null && !factor1.equals(BigInteger.ONE) && !factor1.equals(n)) {
            // Found a non-trivial factor, now find the other factor
            BigInteger factor2 = n.divide(factor1);
            return new BigInteger[]{factor1, factor2};
        }

        return null; // If unable to find a factor
    }

    // Pollard's Rho algorithm for integer factorization
    public static BigInteger pollardsRho(BigInteger n) {
        BigInteger x = BigInteger.valueOf(2);
        BigInteger y = BigInteger.valueOf(2);
        BigInteger one = BigInteger.ONE;
        BigInteger d = BigInteger.ONE;

        // Pollard's Rho function: f(x) = (x^2 + 1) mod n
        while (d.equals(one)) {
            x = x.multiply(x).add(one).mod(n);   // x = f(x)
            y = y.multiply(y).add(one).mod(n);   // y = f(f(y))
            y = y.multiply(y).add(one).mod(n);   // y = f(f(y)) again

            // Calculate the gcd of |x - y| and n
            d = BigInteger.valueOf(ArithmeticUtils.gcd(x.subtract(y).abs().longValue(), n.longValue()));

            // Check if we found a non-trivial divisor
            if (d.compareTo(one) > 0 && d.compareTo(n) < 0) {
                return d;
            }
        }

        return null; // If no factor was found
    }
}
