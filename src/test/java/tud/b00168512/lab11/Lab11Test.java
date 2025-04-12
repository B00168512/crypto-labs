package tud.b00168512.lab11;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Lab11Test {

    @Test
    void decodeStep1() {
        var secret = "hello1";
        var ciphertext = Lab11.step1(secret);
        var plaintext = Lab11.decodeStep1(ciphertext);
        assertEquals(secret, plaintext);
    }

    @Test
    void decodeStep2() {
        var secret = "hello2";
        var ciphertext = Lab11.step2(secret);
        var plaintext = Lab11.decodeStep2(ciphertext);
        assertEquals(secret, plaintext);
    }

    @Test
    void decodeStep3() {
        var secret = "hello3";
        var ciphertext = Lab11.step3(secret);
        var plaintext = Lab11.decodeStep3(ciphertext);
        assertEquals(secret, plaintext);
    }

    @Test
    void decodeSecret1() {
        var secret = "hello secret";
        var rounds = 4;
        var ciphertext = Lab11.makeSecret(secret, rounds);
        var result = Lab11.decryptSecret(ciphertext);
        assertEquals(rounds, result.rounds());
        assertEquals(secret, result.plaintext());
    }

    @Test
    void decodeSecret2() {
        var secret = "Lab 11- Encrypted Evidence File (Challenge Lab)";
        var rounds = 10;
        var ciphertext = Lab11.makeSecret(secret, rounds);
        var result = Lab11.decryptSecret(ciphertext);
        assertEquals(rounds, result.rounds());
        assertEquals(secret, result.plaintext());
    }

    @Test
    void decodeInterceptedMessage() {
        var secret = getInterceptedCiphertext();
        var result = Lab11.decryptSecret(secret);
        assertEquals("Why do elephants have big ears?", result.plaintext());
        assertEquals(67, result.rounds());
    }

    private static String getInterceptedCiphertext() {
        try {
            var resource = "/lab11/intercepted.txt";
            var url = Lab11.class.getResource(resource);
            if (url == null) {
                throw new IllegalArgumentException("Resource does not exist: " + resource);
            }
            return Files.readString(Paths.get(url.toURI()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}