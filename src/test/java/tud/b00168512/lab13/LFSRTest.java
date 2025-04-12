package tud.b00168512.lab13;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class LFSRTest {

    int[][] tapsArray = {
            {1, 6}, {1, 7}, {1, 9},
            {2, 6}, {2, 11},
            {3, 7}, {3, 11},
            {4, 7}, {4, 9},
            {5, 7}, {5, 9}, {5, 11},
            {6, 7}, {6, 11}
    };

    private static final Integer[] PNG_PLAINTEXT_HEADER_FIRST_4_BYTES = Arrays.stream(new String[]{"89", "50", "4e", "47"})
            .map(i -> new BigInteger(i, 16).intValue()).toArray(Integer[]::new);
//            Arrays.copyOfRange(new BigInteger("89504E47", 16).toByteArray(), 1, 5);
    private static final byte[] PNG_PLAINTEXT_HEADER_LAST_4_BYTES = new byte[] {13, 10, 26, 10};

    private static final Integer[] expectedGeneratorOutputForPngHeaderFirst4Bytes =
            Arrays.stream(new String[]{"e9", "45", "bb", "1c"})
                    .map(i -> new BigInteger(i, 16).intValue()).toArray(Integer[]::new);

    final Integer[] knownGeneratorOutputForPngHeader_2 =
            Arrays.stream(new String[]{"4b", "af", "ae", "16"})
                    .map(i -> new BigInteger(i, 16).intValue()).toArray(Integer[]::new);

    private static byte[] encryptedFileBytes;

    private static final byte[] PNG_HEADER_FIRST_4_ENCRYPTED = new byte[4];
    private static final byte[] PNG_HEADER_LAST_4_ENCRYPTED = new byte[4];

    public static void main(String[] args) throws Exception {
        LFSRTest.init();
        var matchedPairs = LFSRTest.findStatesOnFirst4PNGBytes();
        for (MatchedPair matchedPair : matchedPairs) {
            var path = Paths.get(String.format("%s_%s.png",
                    matchedPair.lfsr1().initialRegister(), matchedPair.lfsr2().initialRegister()));
            Files.deleteIfExists(path);
            var file = Files.createFile(path);
            try (var dis = new DataInputStream(new ByteArrayInputStream(encryptedFileBytes));
                 var dos = new DataOutputStream(new FileOutputStream(file.toFile()))) {
                dos.write(Arrays.copyOfRange(new BigInteger("89504e47", 16).toByteArray(), 1, 5));

                dis.read(new byte[4]); //skip known 4 bytes
                var cipherText = dis.readAllBytes();
                var cipherTextByInts = bytesToInts(cipherText);

                var lfsr1 = matchedPair.lfsr1;
                var lfsr2 = matchedPair.lfsr2;

                lfsr1.reset();
                lfsr2.reset();

                for (int i = 0; i < cipherTextByInts.length; i++) {
                    var generatedRandomByte = (lfsr1.getNext()  + lfsr2.getNext()) % 255;
                    var plainTextByte = generatedRandomByte ^ cipherTextByInts[i];
                    dos.write(plainTextByte);
                }
            }
        }
    }


    static void init() {
        try {
            var encryptedImageFile =
                    Paths.get(Lab13_LFSR.class.getClassLoader().getResource("./lab13/flag.enc").toURI());
            encryptedFileBytes = Files.readAllBytes(encryptedImageFile);
            try (var dis = new DataInputStream(new ByteArrayInputStream(encryptedFileBytes))) {
                dis.read(PNG_HEADER_FIRST_4_ENCRYPTED);
                dis.read(PNG_HEADER_LAST_4_ENCRYPTED);
//                System.out.println(new BigInteger(PNG_HEADER_FIRST_4_ENCRYPTED).xor(new BigInteger("89504E47", 16)).toString(16));
//                System.out.println(new BigInteger(PNG_HEADER_LAST_4_ENCRYPTED).xor(new BigInteger("0D0A1A0A", 16)).toString(16));
            }
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * x^12 + x^6 + x^4 + x^1 + 1
     * x^12 + x^9 + x^3 + x^2 + 1
     * x^12 + x^9 + x^8 + x^3 + x^2 + x^1 + 1
     * x^12 + x^10 + x^9 + x^8 + x^6 + x^2 + 1
     * x^12 + x^10 + x^9 + x^8 + x^6 + x^5 + x^4 + x^2 + 1
     * x^12 + x^11 + x^6 + x^4 + x^2 + x^1 + 1
     * x^12 + x^11 + x^9 + x^5 + x^3 + x^1 + 1
     * x^12 + x^11 + x^9 + x^7 + x^6 + x^4 + 1
     * x^12 + x^11 + x^9 + x^7 + x^6 + x^5 + 1
     * x^12 + x^11 + x^9 + x^8 + x^7 + x^4 + 1
     * x^12 + x^11 + x^9 + x^8 + x^7 + x^5 + x^2 + x^1 + 1
     * x^12 + x^11 + x^10 + x^5 + x^2 + x^1 + 1
     * x^12 + x^11 + x^10 + x^8 + x^6 + x^4 + x^3 + x^1 + 1
     * x^12 + x^11 + x^10 + x^9 + x^8 + x^7 + x^5 + x^4 + x^3 + x^1 + 1
     *
     *
     *
     * x^19 + x^5 + x^2 + x^1 + 1
     * x^19 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
     * x^19 + x^6 + x^2 + x^1 + 1
     * x^19 + x^6 + x^5 + x^3 + x^2 + x^1 + 1
     * x^19 + x^6 + x^5 + x^4 + x^3 + x^2 + 1
     * x^19 + x^7 + x^5 + x^3 + x^2 + x^1 + 1
     * x^19 + x^8 + x^7 + x^5 + 1
     * x^19 + x^8 + x^7 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
     * x^19 + x^8 + x^7 + x^6 + x^4 + x^3 + x^2 + x^1 + 1
     * x^19 + x^9 + x^8 + x^5 + 1
     * x^19 + x^9 + x^8 + x^6 + x^5 + x^3 + x^2 + x^1 + 1
     * x^19 + x^9 + x^8 + x^7 + x^4 + x^3 + x^2 + x^1 + 1
     * x^19 + x^11 + x^9 + x^8 + x^7 + x^6 + x^5 + x^4 + x^3 + x^2 + 1
     * x^19 + x^11 + x^10 + x^8 + x^7 + x^5 + x^4 + x^3 + x^2 + x^1 + 1
     * x^19 + x^16 + x^13 + x^10 + x^7 + x^4 + x^1 + 1
     * @return
     */
    private static List<MatchedPair> findStatesOnFirst4PNGBytes() {
        var a_lfsrs = getAllLFSRsWithInitialStates(12, new int[]{1, 6});
        var b_lfsrs = getAllLFSRsWithInitialStates(19, new int[]{4, 10});
        var matchedPairs = new ArrayList<MatchedPair>(100);
//        matchedPairs.add(new MatchedPair(new LFSR(570, new int[]{1, 6}, 12), new LFSR(184739, new int[]{4, 10}, 19)));
//        matchedPairs.add(new MatchedPair(new LFSR(1613, new int[]{1, 6}, 12), new LFSR(37444, new int[]{4, 10}, 19)));
//        matchedPairs.add(new MatchedPair(new LFSR(2059, new int[]{1, 6}, 12), new LFSR(421526, new int[]{4, 10}, 19)));
        for (int a = 0; a < a_lfsrs.length; a++) { // for every A lfsr
            var a_lfsr = a_lfsrs[a];
            for (int b = 0; b < b_lfsrs.length; b++) { // for every B lfsr
                var b_lfsr = b_lfsrs[b];
                var matches = true;
                for (int i = 0; i < 4; i++) { // generate 4 bytes
                    var expectedByte = expectedGeneratorOutputForPngHeaderFirst4Bytes[i];
//                    var b_expectedByte = Modulo.calculateByte(a_lfsr.generated[i], expectedByte);
                    var generatedByte = (a_lfsr.generated[i] + b_lfsr.generated[i]) % 255;
                    if (generatedByte != expectedByte) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    System.out.println(String.format("Match found. a: %s; b: %s",
                            a_lfsr.lfsr().initialRegister(),
                            b_lfsr.lfsr().initialRegister()));
                    matchedPairs.add(new MatchedPair(a_lfsr.lfsr(), b_lfsr.lfsr()));
                }
            }
        }
        return matchedPairs;
    }

    private static LfsrAndGeneratedSequence[] getAllLFSRsWithInitialStates(int registerLength, int[] taps) {
        var initialStates = BitsPermutations.getSeeds(registerLength);
        var lfsrs = new LfsrAndGeneratedSequence[initialStates.size()];
        for (int i = 0; i < initialStates.size(); i++) {
            var lfsr = new LFSR(initialStates.get(i), taps, registerLength);
            var randomBytes = new int[4];
            for (int j = 0; j < 4; j++) {
                randomBytes[j] = lfsr.getNext();
            }
            lfsrs[i] = new LfsrAndGeneratedSequence(lfsr, randomBytes);
        }
        return lfsrs;
    }

    record LfsrAndGeneratedSequence(LFSR lfsr, int[] generated) {

    }

    record MatchedPair(LFSR lfsr1, LFSR lfsr2) {

    }

    private static Integer[] bytesToInts(byte[] bytes) {
        var res = new Integer[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            res[i] = new BigInteger(1, new byte[]{bytes[i]}).intValue();
        }
        return res;
    }

}