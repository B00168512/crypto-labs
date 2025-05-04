package tud.b00168512.cryptohack.math;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.expression.F;

public class SuccessivePowers {

    public static void main(String[] args) {

        F.initSymbols();

        // Results list
        int[] results = {588, 665, 216, 113, 642, 4, 836, 114, 851, 492, 819, 237};

        // Possible p values (prime numbers > max of results)
        int[] possibleP = {
                853, 857, 859, 863, 877, 881, 883, 887, 907,
                911, 919, 929, 937, 941, 947, 953, 967, 971,
                977, 983, 991, 997
        };

        // Compute m
        int m = 216 * 209 - 113;

        System.out.println("m = " + m);

        // Factor m using Symja
        ExprEvaluator evaluator = new ExprEvaluator();
        String expr = "FactorInteger(" + m + ")";
        String result = evaluator.evaluate(expr).toString();

        System.out.println("Factors of m:");
        System.out.println(result);
    }
}
