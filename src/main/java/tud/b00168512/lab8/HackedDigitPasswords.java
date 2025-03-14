package tud.b00168512.lab8;

import java.security.MessageDigest;
import java.security.Security;
import java.util.Set;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Password: 54321;  SHA-1: 06f6fe0f73c6e197ee43eff4e5f7d10fb9e438b2
 * Password: 121298; SHA-1: fafa4483874ec051989d53e1e432ba3a6c6b9143
 */
public class HackedDigitPasswords {

    private static final String SHA_1 = "SHA-1";
    private static final String BC_PROVIDER = "BC";

    private static final String SALT = "www.exploringsecurity.com";

    private static final String TOMTOM_SHA_1_HASH = "06f6fe0f73c6e197ee43eff4e5f7d10fb9e438b2";
    private static final String SECURITY_SHA_1_HASH = "fafa4483874ec051989d53e1e432ba3a6c6b9143";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {
        hashBruteForce(Set.of(SECURITY_SHA_1_HASH, TOMTOM_SHA_1_HASH));
    }

    private static void hashBruteForce(Set<String> targetHashes) throws Exception {
        for (String targetHash : targetHashes) {
            var md = MessageDigest.getInstance(SHA_1, BC_PROVIDER);
            // Password is in digits only and the length is min: 5 max: 7
            for (int i = 10000; i < 10000000; i++) {
                var rawPwd = String.valueOf(i);

                var generatedHashHex = HashGenerator.calculateHashAsHexString(md, SALT.getBytes(), rawPwd.getBytes());

                if (targetHash.equals(generatedHashHex)) {
                    System.out.println("CRACKED!! Password: " + i + "; SHA-1: " + targetHash);
                    break;
                }
            }
        }
    }
}
