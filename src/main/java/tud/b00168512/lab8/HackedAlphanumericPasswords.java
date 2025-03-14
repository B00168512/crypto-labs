package tud.b00168512.lab8;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Password: superman:qwerty;  SHA-1: 437fbc6892b38db6ac5bdbe2eab3f7bc924527d9
 * Password: mark123:Mark123;  SHA-1: 92e54f10103a3c511853c7098c04141f114719c1
 * Password: sparky:Admin09;   SHA-1: 2834da08d58330d8dafbb2ac1c0f85f6b3b135ef
 */
public class HackedAlphanumericPasswords {

    private static final String SHA_1 = "SHA-1";
    private static final String BC_PROVIDER = "BC";

    private static final String SALT = "www.exploringsecurity.com";

    private static final String MARK_123_SHA_1_HASH = "92e54f10103a3c511853c7098c04141f114719c1";
    private static final String SUPERMAN_SHA_1_HASH = "437fbc6892b38db6ac5bdbe2eab3f7bc924527d9";
    private static final String SPARKY_SHA_1_HASH =   "2834da08d58330d8dafbb2ac1c0f85f6b3b135ef";
    private static final String JillC_SHA_1_HASH =    "f44f3b09df53c1c11273def13cacd8922a86d48c";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {
        crackWithDictionary("F:/pwds/L6");
    }

    private static void crackWithDictionary(String fileName) throws Exception {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available processors: " + cores);

        var executor = Executors.newFixedThreadPool(cores);

        try (var files = Files.list(Paths.get(fileName))) {
            files.forEach(file -> {
                executor.submit(() -> {
                    System.out.println("Using file: " + file.getFileName());
                    try (var linesStream = Files.lines(file, StandardCharsets.ISO_8859_1)) {
                        var md = MessageDigest.getInstance(SHA_1, BC_PROVIDER);
                        linesStream
                                .forEach(pwd -> {
                                    var hash = HashGenerator.calculateHashAsHexString(md, SALT.getBytes(), pwd.toLowerCase().getBytes());
                                    if (JillC_SHA_1_HASH.equals(hash)) {
                                        System.out.println("CRACKED! Password: " + pwd + "; SHA-1: " + JillC_SHA_1_HASH);
                                    }
                                });
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            });

        }

        executor.shutdown();
    }

    private static void submit(List<String> passwords, ExecutorService executor) {
        executor.submit(() -> {
            try {
                var md = MessageDigest.getInstance(SHA_1, BC_PROVIDER);

                passwords.stream()
                        .filter(l -> l.length() > 4 && l.length() < 8)
                        .forEach(pwd -> {
                    var hash = HashGenerator.calculateHashAsHexString(md, SALT.getBytes(), pwd.getBytes());
                    if (JillC_SHA_1_HASH.equals(hash)) {
                        System.out.println("CRACKED! Password: " + pwd + "; SHA-1: " + JillC_SHA_1_HASH);
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
