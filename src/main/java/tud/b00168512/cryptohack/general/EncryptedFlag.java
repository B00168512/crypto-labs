package tud.b00168512.cryptohack.general;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.StringJoiner;

import org.apache.commons.codec.DecoderException;

import org.apache.commons.codec.binary.Hex;

public class EncryptedFlag {

    public static void main(String[] args) throws DecoderException {
        var encoded = new byte[]{116, 104, 111, 110, 103, 115, 95, 97, 99, 99, 101, 115, 115, 95, 118, 97, 108, 108, 101, 121};
//        var st1r = new BigInteger(new String(encoded)).toByteArray();
//        var str = Base64.getDecoder().decode(encoded);
        System.out.println("Decoded: " + new String(encoded, StandardCharsets.UTF_8));

        var message = "0e0b213f26041e480b26217f27342e175d0e070a3c5b103e2526217f27342e175d0e077e263451150104";
        var msgByteArray = new BigInteger(message, 16).toByteArray();
        printArray("Encrypted message", msgByteArray);


        var secretGuess = "crypto{";
        var secretGuessBytes = secretGuess.getBytes(StandardCharsets.US_ASCII);
        printArray("Secret guess     ", secretGuessBytes);

        var myXORkeyBytes = "myXORkeymyXORkeymyXORkeymyXORkeymyXORkeymy".getBytes();
        var msg = Arrays.copyOf(new BigInteger(message, 16).toByteArray(), myXORkeyBytes.length);
        var a = new BigInteger(msg).xor(new BigInteger(myXORkeyBytes));
        System.out.println(new String(a.toByteArray()));
    }

    private static void printArray(String prefix, byte[] arr) {
        System.out.println(prefix + ": " + Arrays.toString(arr));
    }
}
