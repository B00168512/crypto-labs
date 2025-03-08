package tud.b00168512.lab8;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.Security;
import java.util.List;
import java.util.concurrent.Executors;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * CRACKED! Password: qwerty; SHA-1: 437fbc6892b38db6ac5bdbe2eab3f7bc924527d9
 */
public class HackedAlphanumericPasswords {

    private static final String SHA_1 = "SHA-1";
    private static final String BC_PROVIDER = "BC";

    private static final String SALT = "www.exploringsecurity.com";

    private static final String DIGIT_HASH_3 = "92e54f10103a3c511853c7098c04141f114719c1";
    private static final String DIGIT_HASH_4 = "f44f3b09df53c1c11273def13cacd8922a86d48c";
    private static final String DIGIT_HASH_5 = "437fbc6892b38db6ac5bdbe2eab3f7bc924527d9";
    private static final String DIGIT_HASH_6 = "2834da08d58330d8dafbb2ac1c0f85f6b3b135ef";

    private static final byte[] DIGIT_HASH_3_BYTES = new BigInteger(DIGIT_HASH_3, 16).toByteArray();
    private static final byte[] DIGIT_HASH_4_BYTES = new BigInteger(DIGIT_HASH_4, 16).toByteArray();
    private static final byte[] DIGIT_HASH_5_BYTES = new BigInteger(DIGIT_HASH_5, 16).toByteArray();
    private static final byte[] DIGIT_HASH_6_BYTES = new BigInteger(DIGIT_HASH_6, 16).toByteArray();

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available processors: " + cores);

        var executor = Executors.newFixedThreadPool(cores);

        for (var pwd : getTopPasswords()) {
            executor.submit(() -> {
                try {
                    var md = MessageDigest.getInstance(SHA_1, BC_PROVIDER);
                    var hash = HashGenerator.calculateHash(md, SALT.getBytes(), pwd.getBytes());
                    if (MessageDigest.isEqual(hash, DIGIT_HASH_3_BYTES)) {
                        System.out.println("CRACKED! Password: " + pwd + "; SHA-1: " + DIGIT_HASH_3);
                    } else if (MessageDigest.isEqual(hash, DIGIT_HASH_4_BYTES)) {
                        System.out.println("CRACKED! Password: " + pwd + "; SHA-1: " + DIGIT_HASH_4);
                    } else if (MessageDigest.isEqual(hash, DIGIT_HASH_5_BYTES)) {
                        System.out.println("CRACKED! Password: " + pwd + "; SHA-1: " + DIGIT_HASH_5);
                    } else if (MessageDigest.isEqual(hash, DIGIT_HASH_6_BYTES)) {
                        System.out.println("CRACKED! Password: " + pwd + "; SHA-1: " + DIGIT_HASH_6);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();
    }

    private static List<String> getTopPasswords() throws Exception {
        var path = "/lab8/xato-net-10-million-passwords-1000000.txt";
        var file = HackedSaltWord.class.getResource(path);
        if (file == null) {
            throw new IllegalArgumentException(String.format("Cannot find '%s' file in classpath.", path));
        }
        try (var linesSteam = Files.lines(Paths.get(file.toURI()))) {
            return linesSteam.filter(line -> line.length() > 4 && line.length() < 8).toList();
        }
    }

}
