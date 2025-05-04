package tud.b00168512.cryptohack.math;

public class ChineseReminderTheorem {

    public static void main(String[] args) {
        long a1 = 2;
        long m1 = 5;
        long a2 = 3;
        long m2 = 11;
        long a3 = 5;
        long m3 = 17;

        long N = m1 * m2 * m3;

        long n1 = N / m1;
        long n2 = N / m2;
        long n3 = N / m3;

        long x1 = 3;
        long x2 = 7;
        long x3 = 13;

        long e1 = n1 * x1;
        long e2 = n2 * x2;
        long e3 = n3 * x3;

        long x = (a1 * e1 + a2 * e2 + a3 * e3) % N;

        System.out.println("Result = " + x);
    }
}
