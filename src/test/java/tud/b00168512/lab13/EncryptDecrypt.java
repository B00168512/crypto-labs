package tud.b00168512.lab13;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class EncryptDecrypt {

    public static void main(String[] args) throws Exception {

        var pngFile =
                Paths.get(Lab13_LFSR.class.getClassLoader().getResource("./lab13/sample-red-400x300.png").toURI());

        var encryptedFilePath = Paths.get("encrypted_sample-red-400x300.png");
        Files.deleteIfExists(encryptedFilePath);
        Files.createFile(encryptedFilePath);

        var matchedPairs = new ArrayList<LFSRTest.MatchedPair>(100);
        matchedPairs.add(new LFSRTest.MatchedPair(new LFSR(570, new int[]{1, 6}, 12), new LFSR(184739, new int[]{4, 10}, 19)));
        matchedPairs.add(new LFSRTest.MatchedPair(new LFSR(1613, new int[]{1, 6}, 12), new LFSR(37444, new int[]{4, 10}, 19)));
        matchedPairs.add(new LFSRTest.MatchedPair(new LFSR(2059, new int[]{1, 6}, 12), new LFSR(421526, new int[]{4, 10}, 19)));

        var lfsrs = matchedPairs.get(0);

        try(var dis = new DataInputStream(new FileInputStream(pngFile.toFile()));
            var dos = new DataOutputStream(new FileOutputStream(encryptedFilePath.toFile()))) {
            dis.read(new byte[4]);
            var plainText = dis.readAllBytes();



            for (int i = 0; i < plainText.length; i++) {
                var plainByte = plainText[i];
                var a_byte = lfsrs.lfsr1().getNext();
                var b_byte = lfsrs.lfsr2().getNext();
                var generatedRandom = (a_byte + b_byte) % 255;
                var encryptedByte = generatedRandom ^ plainByte;
                dos.write(encryptedByte);
            }
        }

//        // decrypt
//
        var decryptedPngFilePath = Paths.get("decrypted-sample-red-400x300.png");
        Files.deleteIfExists(decryptedPngFilePath);
        Files.createFile(decryptedPngFilePath);

        try(var dis = new DataInputStream(new FileInputStream(encryptedFilePath.toFile()));
            var dos = new DataOutputStream(new FileOutputStream(decryptedPngFilePath.toFile()))) {
            dos.write(Arrays.copyOfRange(new BigInteger("89504e47", 16).toByteArray(), 1, 5));

            var cipherText = dis.readAllBytes();
            var cipherTextByInts = bytesToInts(cipherText);

            var lfsr1 = lfsrs.lfsr1();
            var lfsr2 = lfsrs.lfsr2();

            lfsr1.reset();
            lfsr2.reset();

            for (int i = 0; i < cipherTextByInts.length; i++) {
                var generatedRandomByte = (lfsr1.getNext()  + lfsr2.getNext()) % 255;
                var plainTextByte = generatedRandomByte ^ cipherTextByInts[i];
                dos.write(plainTextByte);
            }
        }
    }

    private static Integer[] bytesToInts(byte[] bytes) {
        var res = new Integer[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            res[i] = new BigInteger(1, new byte[]{bytes[i]}).intValue();
        }
        return res;
    }


}
