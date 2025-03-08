package tud.b00168512.lab8;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.Security;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This program parses Lab 8 document (see 'parsed_words.txt' file) and extracts all unique 'space separated' words
 * For every word W (about 200 words) it generaates all possible SHA-1 hashes in range from 10000 to 9999999 passwords.
 * The result is a salt: www.exploringsecurity.com
 */
public class HackedSaltWord {

    private static final String SHA_1 = "SHA-1";
    private static final String BC_PROVIDER = "BC";

    private static final byte[] OLD_HASH_1 = new BigInteger("06f6fe0f73c6e197ee43eff4e5f7d10fb9e438b2", 16).toByteArray();
    private static final byte[] OLD_HASH_2 = new BigInteger("fafa4483874ec051989d53e1e432ba3a6c6b9143", 16).toByteArray();

    private static final int MIN_DIGIT_PASSWORD = 10000;
    private static final int MAX_DIGIT_PASSWORD = 9999999;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available processors: " + cores);

        var saltWords = extractSaltWords();
        System.out.println("Possible salt words: " + saltWords.size() + " = " + saltWords);

        var executor = Executors.newFixedThreadPool(cores);

        for (String salt : saltWords) {
            executor.submit(() -> {
                try {
                    var md = MessageDigest.getInstance(SHA_1, BC_PROVIDER);
                    // Password is in digits only and the length is min: 5 max: 7
                    for (int i = 0; i <= MAX_DIGIT_PASSWORD; i++) {
                        var rawPwd = "";
                        if (i < MIN_DIGIT_PASSWORD) {
                            rawPwd = String.format("%05d", i);
                        } else {
                            rawPwd = String.valueOf(i);
                        }
                        var hash = HashGenerator.calculateHash(md, salt.getBytes(), rawPwd.getBytes());
                        if (MessageDigest.isEqual(hash, OLD_HASH_1) || MessageDigest.isEqual(hash, OLD_HASH_2)) {
                            System.out.println("CRACKED! Password: " + i + "; salt: " + salt);
                            break;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();
    }


    private static Set<String> extractSaltWords() throws Exception {
        var path = "/lab8/parsed_words.txt";
        var file = HackedSaltWord.class.getResource(path);
        if (file == null) {
            throw new IllegalArgumentException(String.format("Cannot find '%s' file in classpath.", path));
        }
        var bytes = Files.readAllBytes(Paths.get(file.toURI()));
        var str = new String(bytes, StandardCharsets.UTF_8);
        str = str.replaceAll("\n", " ");
        var split = str.split(" ");
        var res = new HashSet<String>(split.length);
        for (String s : split) {
            var word = s.trim();
            if (word.length() <= 3) {
                continue;
            }
            res.add(word);
        }
        return res;
    }
}
