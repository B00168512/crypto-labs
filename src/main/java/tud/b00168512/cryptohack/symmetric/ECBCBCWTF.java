package tud.b00168512.cryptohack.symmetric;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ECBCBCWTF {

    public static void main(String[] args) {
        var ciphertextHex = "13afaa724bc5be8cbf68705dee2b00765537bd9b07658967ff19603cef050aa43247f6af2fad915bb98ddb561d5791e4";
        var plaintextHex = "ef9dd4512f350e92cbcfc3f5c2a9302770ddd3023faac5bfdc0a2f689b486b430a03cbab3601d656c846411dce242bd9";

        var ciphertextIV = "13afaa724bc5be8cbf68705dee2b0076"; // first 16 bytes (32 hex chars)
        var ciphertext = "5537bd9b07658967ff19603cef050aa43247f6af2fad915bb98ddb561d5791e4";// [17...length] 32 bytes / 64 hex chars

        var plaintextFirstBlock = "ef9dd4512f350e92cbcfc3f5c2a93027";
        var plaintextFirstBlockXORed = new BigInteger(plaintextFirstBlock, 16).xor(new BigInteger(ciphertextIV, 16));

        var plaintextSecondBlock = "70ddd3023faac5bfdc0a2f689b486b430a03cbab3601d656c846411dce242bd9";
        var plaintextSecondBlockXORed = new BigInteger(plaintextSecondBlock, 16).xor(new BigInteger(ciphertext, 16));

        var firstBlockBytes = plaintextFirstBlockXORed.toByteArray();
        var secondBlockBytes = plaintextSecondBlockXORed.toByteArray();
        var bb = ByteBuffer.allocate(firstBlockBytes.length + secondBlockBytes.length).put(firstBlockBytes).put(secondBlockBytes);
        System.out.println(new String(bb.array()));

    }
}
