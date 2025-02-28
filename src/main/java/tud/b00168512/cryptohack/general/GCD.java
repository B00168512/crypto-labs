package tud.b00168512.cryptohack.general;

public class GCD {

    private static final int A = 26513;
    private static final int B = 32321;
    private static final int GCD = gcd(A, B);

    public static void main(String[] args) {
//        System.out.println(gcd(240, 46)); // 5520
        gcdExtended(26513, 32321, 1, 0, 0, 1);
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, (a % b));
    }

    public static int gcdBySubtraction(int a, int b) {
        if (a == b) {
            return a;
        }
        if (a > b) {
            return gcdBySubtraction(a - b, b);
        } else {
            return gcdBySubtraction(b - a, a);
        }
    }

    public static void gcdExtended(int a, int b, int si1, int si2, int ti1, int ti2) {
        var quotient = Math.floorDiv(a, b);
        var reminder = a % b;
        var x_si1 = si1 - (quotient * si2);
        var y_ti1 = ti1 - (quotient * ti2);
//        System.out.println("Quotient: " + quotient + "; reminder: " + reminder + "; si: " + x_si1 + "; ti: " + y_ti1);
        if (reminder == 0) {
            System.out.println("Finished");
            return;
        }
        if (testExtended(A, B, x_si1, y_ti1)) {
            return;
        }
        gcdExtended(b, reminder, si2, x_si1, ti2, y_ti1);

    }

    private static boolean testExtended(int a, int b, int u, int v) {
        System.out.println("U=" + u + "; V=" + v);
        if ((a*u) + (b*v) == GCD) {
            System.out.println("Matching: " + "U=" + u + "; V=" + v);
            return true;
        }
        return false;
    }
}
