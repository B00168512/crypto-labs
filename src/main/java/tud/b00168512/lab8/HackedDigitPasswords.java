package tud.b00168512.lab8;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Security;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * CRACKED! Password: 54321; SHA-1: 06f6fe0f73c6e197ee43eff4e5f7d10fb9e438b2
 */
public class HackedDigitPasswords {

    private static final String SHA_1 = "SHA-1";
    private static final String BC_PROVIDER = "BC";

    private static final String SALT = "www.exploringsecurity.com";

    private static final String DIGIT_HASH_1 = "06f6fe0f73c6e197ee43eff4e5f7d10fb9e438b2";
    private static final String DIGIT_HASH_2 = "fafa4483874ec051989d53e1e432ba3a6c6b9143";

    private static final List<String> OLD_HASHES = List.of(DIGIT_HASH_1, DIGIT_HASH_2);

    private static final int MIN_DIGIT_PASSWORD = 10000;
    private static final int MAX_DIGIT_PASSWORD = 9999999;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {

        var md = MessageDigest.getInstance(SHA_1, BC_PROVIDER);

        for (var digitHash : OLD_HASHES) {
            var hashAsBytes = new BigInteger(digitHash, 16).toByteArray();
            // Password is in digits only and the length is min: 5 max: 7
            for (int i = 0; i <= MAX_DIGIT_PASSWORD; i++) {
                var rawPwd = "";
                if (i < MIN_DIGIT_PASSWORD) {
                    rawPwd = String.format("%05d", i);
                } else {
                    rawPwd = String.valueOf(i);
                }
                var generatedHash = HashGenerator.calculateHash(md, SALT.getBytes(), rawPwd.getBytes());
                if (MessageDigest.isEqual(hashAsBytes, generatedHash)) {
                    System.out.println("CRACKED! Password: " + i + "; SHA-1: " + digitHash);
                    break;
                }
            }
        }
    }
}
