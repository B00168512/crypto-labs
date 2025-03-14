package tud.b00168512.lab8;

import java.math.BigInteger;
import java.security.MessageDigest;

public final class HashGenerator {

    static String calculateHashAsHexString(MessageDigest md, byte[] salt, byte[] input) {
        var hashHex = new BigInteger(1, calculateHash(md, salt, input)).toString(16);
        while (hashHex.length() < 40) {
            hashHex = "0" + hashHex;
        }
        return hashHex;
    }

    static byte[] calculateHash(MessageDigest md, byte[] salt, byte[] input) {
        md.update(salt);
        var res = md.digest(input);
        md.reset();
        return res;
    }
}
