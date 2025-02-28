package tud.b00168512.lab1c;

public class ModuloE {

    public static void main(String[] args) {
        long base = 8;
        long exponent = 5;
        long modulus = 269;

        long result = powerMod(base, exponent, modulus);
        System.out.println("(" + base + "^" + exponent + ") mod " + modulus + " = " + result);

    }

    public static long powerMod(long base, long exponent, long modulus) {
        long result = 1;
        base = base % modulus;

        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            base = (base * base) % modulus;
            exponent /= 2;
        }
        return result;
    }
}
