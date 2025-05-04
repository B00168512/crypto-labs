package tud.b00168512.cryptohack.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class FactoriseNGivenD {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static void main(String[] args) {
        var privateKey = new BigInteger("21711308225346315542706844618441565741046498277716979943478360598053144971379956916575370343448988601905854572029635846626259487297950305231661109855854947494209135205589258643517961521594924368498672064293208230802441077390193682958095111922082677813175804775628884377724377647428385841831277059274172982280545237765559969228707506857561215268491024097063920337721783673060530181637161577401589126558556182546896783307370517275046522704047385786111489447064794210010802761708615907245523492585896286374996088089317826162798278528296206977900274431829829206103227171839270887476436899494428371323874689055690729986771");
        var d = new BigInteger("2734411677251148030723138005716109733838866545375527602018255159319631026653190783670493107936401603981429171880504360560494771017246468702902647370954220312452541342858747590576273775107870450853533717116684326976263006435733382045807971890762018747729574021057430331778033982359184838159747331236538501849965329264774927607570410347019418407451937875684373454982306923178403161216817237890962651214718831954215200637651103907209347900857824722653217179548148145687181377220544864521808230122730967452981435355334932104265488075777638608041325256776275200067541533022527964743478554948792578057708522350812154888097");
        var e = 17;
        var friendsPublicKeys = new int[]{106979, 108533, 69557, 97117, 103231};

        test(BigInteger.valueOf(103231), 17, d);

//        test(
//                new BigInteger("a66791dc6988168de7ab77419bb7fb0c001c62710270075142942e19a8d8c51d053b3e3782a1de5dc5af4ebe99468170114a1dfe67cdc9a9af55d655620bbab", 16),
//                17,
//                new BigInteger("123c5b61ba36edb1d3679904199a89ea80c09b9122e1400c09adcf7784676d01d23356a7d44d6bd8bd50e94bfc723fa87d8862b75177691c11d757692df8881", 16));
    }

    private static void test(BigInteger N, int e, BigInteger d) {
        var k = d.multiply(BigInteger.valueOf(e)).subtract(BigInteger.ONE);
        test(N, k, 2, k, new AtomicBoolean());
    }

    private static void test(BigInteger N, BigInteger k, int g, BigInteger t, AtomicBoolean found) {
        if (found.get()) {
            return;
        }
        if (t.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            var newT = t.divide(BigInteger.TWO);
            System.out.println("New t: " + newT);
            var newX = BigInteger.valueOf(g).modPow(newT, N);
            if (newX.equals(BigInteger.ONE)) {
                test(N, k, g, newT, found);
            } else {
                var newY = gcd(newX.subtract(BigInteger.ONE).intValue(), N.intValue());
                if (newY > 1) {
                    var p = newY;
                    var q = N.divide(BigInteger.valueOf(newY));
                    System.out.println("p = " + p + "; q = " + q);
                    System.out.println(q.toString(16));
                    found.set(true);
                } else {
                    test(N, k, g, newT, found);
                }
            }
            test(N, k, getRandom(N).intValue(), k, found);
        }
    }

    private static BigInteger getRandom(BigInteger n) {
        var nextRandom = (BigInteger) null;
        do {
            nextRandom = new BigInteger(n.bitLength(), RANDOM); // Generate a number with same bit length as N
        } while (nextRandom.compareTo(BigInteger.TWO) < 0 || nextRandom.compareTo(n) >= 0);  // Ensure number is in the correct range

        return nextRandom;
    }
    public static int gcd(int a, int b) {
        // Continue until b becomes zero
        while (b != 0) {
            int temp = b;
            b = a % b;  // The remainder of a divided by b
            a = temp;   // Set a to the previous value of b
        }
        return a;  // When b is zero, a contains the GCD
    }

}
