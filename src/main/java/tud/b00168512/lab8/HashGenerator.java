package tud.b00168512.lab8;

import java.security.MessageDigest;

public final class HashGenerator {

    static byte[] calculateHash(MessageDigest md, byte[] salt, byte[] input) {
        md.update(salt);
        var res = md.digest(input);
        md.reset();
        return res;
    }
}
