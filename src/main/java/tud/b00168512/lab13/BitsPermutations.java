package tud.b00168512.lab13;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public class BitsPermutations {

    public static List<boolean[]> get(int length) {
        if (length <= 0) {
            return Collections.emptyList();
        }
        int totalPermutations = 1 << length; // 2 raised to the power of length

        var result = new ArrayList<boolean[]>(totalPermutations);

        for (int i = 0; i < totalPermutations; i++) {
            String binaryString = Integer.toBinaryString(i);

            // Pad with leading zeros to ensure the string has the desired length
            while (binaryString.length() < length) {
                binaryString = "0" + binaryString;
            }
            var b = new boolean[binaryString.length()];
            for (int i1 = 0; i1 < binaryString.length(); i1++) {
                b[i1] = binaryString.charAt(i1) == '1';
            }

            result.add(b);
        }
        Collections.shuffle(result);
        return result;
    }

    public static List<Integer> getSeeds(int length) {
        if (length <= 0) {
            return Collections.emptyList();
        }
        int totalPermutations = 1 << length; // 2 raised to the power of length

        var result = new ArrayList<Integer>(totalPermutations);

        for (int i = 0; i < totalPermutations; i++) {
            result.add(i);
        }
        Collections.shuffle(result);
        return result;
    }

    public static List<boolean[]> generateAllBitSequences(int length) {
        if (length < 0) {
            System.err.println("Error: Length must be non-negative.");
            return Collections.emptyList();
        }
        if (length > 63) {
            // Long.toBinaryString limit; practically, memory/time is the issue much sooner
            System.err.println("Error: Length > 63 may cause issues with Long representation.");
            // Consider alternatives like BigInteger or recursion for very large lengths.
        }

        // Calculate the total number of sequences: 2^length
        // Using bit shift (1L << length) is efficient for powers of 2. Use 1L for long.
        long totalSequences = 1L << length;
        // Or use: long totalSequences = (long) Math.pow(2, length);

        var list = new ArrayList<boolean[]>((int) totalSequences);

        // Loop from 0 up to (2^length) - 1
        for (long i = 0; i < totalSequences; i++) {
            // Convert the number to its binary representation
            String binaryString = Long.toBinaryString(i);

            // Pad with leading zeros if necessary to reach the desired length
            // String.format provides a concise way to pad:
            // % : start format specifier
            // length : minimum width for the output string
            // s : format the argument as a string
            // replace(' ', '0') : replaces the space padding added by String.format with '0'
            String paddedString = String.format("%" + length + "s", binaryString).replace(' ', '0');

            var b = new boolean[paddedString.length()];
            for (int j = 0; j < paddedString.length(); j++) {
                b[j] = paddedString.charAt(j) == '1';
            }

            list.add(b);
        }


        return list;
    }
}
