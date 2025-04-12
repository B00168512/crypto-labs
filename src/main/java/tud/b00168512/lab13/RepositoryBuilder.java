package tud.b00168512.lab13;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class RepositoryBuilder {

    private static final BigInteger mod_255 = BigInteger.valueOf(255);

//    private static final BigInteger PNG_PLAINTEXT_HEADER = new BigInteger("89 50 4E 47 0D 0A 1A 0A", 16);

    private static final Integer[] knownGeneratorOutputForPngHeader =
            Arrays.stream(new String[]{"e9", "45", "bb", "1c", "4b", "af", "ae", "16"})
                    .map(i -> new BigInteger(i, 16).intValue()).toArray(Integer[]::new);

    public static void main(String[] args) throws Exception {
        var a = 0xD34;
        var b = 0x593CA;
        var a_a = new int[] {0xD34, 0x69A, 0x34D, 0xC92};
        System.out.println(a);
        System.out.println(b);
//        reverseCipher();
//        search();
//        searchAll();
    }

//    private static RegisterInfo[] generate(int registerSize, int tap1, int tap2) {
//        var permutations = BitsPermutations.generateAllBitSequences(registerSize);
//        var registerInfos = new RegisterInfo[permutations.size()];
//
//        for (int i = 0; i < permutations.size(); i++) {
//            var lfsrRegister = new LFSR(permutations.get(i), tap1, tap2);
//            var randomBytes = new int[8];
//            for (var j = 0; j < 8; j++) {
//                randomBytes[j] = lfsrRegister.generate();
//            }
//
//            var registerInfo = new RegisterInfo(permutations.get(i), randomBytes);
//            registerInfos[i] = registerInfo;
//        }
//
//        return registerInfos;
//    }

//    private static void searchAll() {
//        var r1 = generate(12, 1, 6);
//        var r2 = generate(19, 4, 10);
//
//
//        for (int i = 0; i < r1.length; i++) {
//            var register_a = r1[i];
//            var ints_a = register_a.generatedOutput();
//            for (int j = 0; j < r2.length; j++) {
//                var register_b = r2[j];
//                var ints_b = register_b.generatedOutput();
//
//                var matched = true;
//                for (int k = 0; k < knownGeneratorOutputForPngHeader.length; k++) {
//                    var expectedGeneratedByte = knownGeneratorOutputForPngHeader[k];
//                    var actualGeneratedByte = (ints_a[k] + ints_b[k]) % 255;
//                    if (actualGeneratedByte != expectedGeneratedByte) {
//                        matched = false;
//                        if (k > 2) {
//                            System.out.println("FOUND: " + k);
//                        }
//                        break;
//                    }
//                }
//                if (matched) {
//                    System.out.println(String.format("Match found: A: %s; B: %s",
//                            Arrays.toString(register_a.initialRegister()), Arrays.toString(register_b.initialRegister())));
//                }
//            }
//        }
//    }
//
//    private static void search() throws Exception {
//        var p_a = new ArrayList<int[]>(100);
//        var p_b = new ArrayList<int[]>(100);
//        var r1 = generate(12, 1, 6);
//        var r2 = generate(19, 4, 10);
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
//                        if (k == 0) {
//                            p_a.add(ints_a);
//                            p_b.add(ints_b);
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
//
//        System.out.println("COMPLETED");
//        var f_a = Paths.get(RepositoryBuilder.class.getClassLoader().getResource("./lab13/a_2.txt").toURI());
//        var f_b = Paths.get(RepositoryBuilder.class.getClassLoader().getResource("./lab13/b_2.txt").toURI());
//        try (BufferedWriter writer_a = new BufferedWriter(new FileWriter(f_a.toFile()));
//             BufferedWriter writer_b = new BufferedWriter(new FileWriter(f_b.toFile()))) {
//            for (int i = 0; i < p_a.size(); i++) {
//                writer_a.write(Arrays.toString(p_a.get(i)));
//                writer_b.write(Arrays.toString(p_b.get(i)));
//                writer_a.newLine();
//                writer_b.newLine();
//                writer_a.flush();
//            }
//        }
//        catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }

}
