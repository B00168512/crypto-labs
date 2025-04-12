package tud.b00168512.lab13;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Lab13_LFSR {

    private static final BigInteger PNG_PLAINTEXT_HEADER = new BigInteger("89504E470D0A1A0A", 16);
    private static final BigInteger PNG_PLAINTEXT_HEADER_FIRST_4_BYTES = new BigInteger("89504e47", 16);
    private static final BigInteger PNG_PLAINTEXT_HEADER_LAST_4_BYTES = new BigInteger("0D0A1A0A", 16);

    public static void main(String[] args) throws Exception {
        var encryptedImageFile = Paths.get(Lab13_LFSR.class.getClassLoader().getResource("./lab13/flag.enc").toURI());
        decryptFileWithLfsr(encryptedImageFile);

    }

    private static void decryptFileWithLfsr(Path encryptedFile) throws Exception {
        var allBytes = Files.readAllBytes(encryptedFile);
        try (var dis = new DataInputStream(new ByteArrayInputStream(allBytes))) {
            var header = new byte[4];
            dis.read(header);

            final Integer[] knownGeneratorOutputForPngHeader_1 =
                    Arrays.stream(new String[]{"e9", "45", "bb", "1c"})
                            .map(i -> new BigInteger(i, 16).intValue()).toArray(Integer[]::new);

            final Integer[] knownGeneratorOutputForPngHeader_2 =
                    Arrays.stream(new String[]{"4b", "af", "ae", "16"})
                            .map(i -> new BigInteger(i, 16).intValue()).toArray(Integer[]::new);

//            var first4BytesMatches = decryptHeader(knownGeneratorOutputForPngHeader_1);
//            var last4BytesMatches = decryptHeader2(first4BytesMatches, knownGeneratorOutputForPngHeader_2);
//            System.out.println("first 4 bytes matches = " + first4BytesMatches.size());
//            System.out.println("last 4 bytes matches = " + last4BytesMatches.size());

        }
    }

//    private static List<Result> decryptHeader(Integer[] knownGeneratorOutputForPngHeader) {
//        var res = new LinkedList<Result>();
//        var r1 = generate(12, 1, 6, 4);
//        var r2 = generate(19, 4, 10, 4);
//        for (int i = 0; i < r1.length; i++) {
//            var register_a = r1[i];
//            var ints_a = register_a.generatedOutput();
//            for (int j = 0; j < r2.length; j++) {
//                var register_b = r2[j];
//                var ints_b = register_b.generatedOutput();
//                var matched = true;
//                for (int k = 0; k < knownGeneratorOutputForPngHeader.length; k++) {
//                    var expectedGeneratedByte = knownGeneratorOutputForPngHeader[k];
//                    var guessedR2Byte = Modulo.calculateByte(ints_a[k], expectedGeneratedByte);
//                    if (ints_b[k] != guessedR2Byte) {
//                        if (k == 3) {
//                            res.add(new Result(register_a.initialRegister(), register_b.initialRegister()));
//                        }
//                        matched = false;
//                        break;
//                    }
//                }
//                if (matched) {
//                    System.out.println(String.format("Match found: A: %s; B: %s",
//                            Arrays.toString(register_a.initialRegister()), Arrays.toString(register_b.initialRegister())));
//                }
//            }
//
//        }
//        return res;
//    }

//    private static List<Result> decryptHeader2(List<Result> initialRegisters, Integer[] knownGeneratorOutputForPngHeader) {
//        var res = new LinkedList<Result>();
//        for (int i = 0; i < initialRegisters.size(); i++) {
//            var register_a = initialRegisters.get(i).a();
//            var lfsr_a = new LFSR(register_a, 1, 6);
//
//            var randomBytes_a = new int[4];
//            for (var j = 0; j < 4; j++) {
//                randomBytes_a[j] = lfsr_a.generate();
//            }
//
//            for (int j = 0; j < initialRegisters.size(); j++) {
//                var register_b = initialRegisters.get(j).b();
//                var lfsr_b =
//                        new LFSR(register_b, 4, 10);
//                var randomBytes_b = new int[4];
//                for (var t = 0; t < 4; t++) {
//                    randomBytes_b[t] = lfsr_b.generate();
//                }
//
//                var matched = true;
//                for (int k = 0; k < knownGeneratorOutputForPngHeader.length; k++) {
//                    var expectedGeneratedByte = knownGeneratorOutputForPngHeader[k];
//                    var guessedR2Byte = Modulo.calculateByte(randomBytes_a[k], expectedGeneratedByte);
//                    if (randomBytes_b[k] != guessedR2Byte) {
//                        if (k == 2) {
//                            res.add(new Result(register_a, register_b));
//                        }
//                        matched = false;
//                        break;
//                    }
//                }
//            }
//
//        }
//        return res;
//    }

//    private static RegisterInfo[] generate(int registerSize, int tap1, int tap2, int outputSize) {
//        var permutations = BitsPermutations.generateAllBitSequences(registerSize);
//        var registerInfos = new RegisterInfo[permutations.size()];
//
//        for (int i = 0; i < permutations.size(); i++) {
//            var lfsrRegister = new LFSR(permutations.get(i), tap1, tap2);
//            var randomBytes = new int[outputSize];
//            for (var j = 0; j < outputSize; j++) {
//                randomBytes[j] = lfsrRegister.generate();
//            }
//
//            var registerInfo = new RegisterInfo(permutations.get(i), randomBytes);
//            registerInfos[i] = registerInfo;
//        }
//
//        return registerInfos;
//    }

    record Result(boolean[] a, boolean[] b) {

    };

}
