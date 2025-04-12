package tud.b00168512.lab11;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Decrypts ciphertext by simply re-applying steps 1, 2 and 3 in reverse order.
 * Decrypting 'intercepted.txt' file results in plaintext message: "Why do elephants have big ears?".
 * It was calculated that the encryption was done in 67 rounds.
 */
public class Lab11 {

    private static final String ASCII_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

    private static final String TRANSLATE_FROM = "zyxwvutsrqponZYXWVUTSRQPONmlkjihgfedcbaMLKJIHGFEDCBA";
    private static final String TRANSLATE_TO = "mlkjihgfedcbaMLKJIHGFEDCBAzyxwvutsrqponZYXWVUTSRQPON";

    private static final Map<Character, Character> TRANSLATION_TABLE;
    private static final Map<Character, Character> REVERSE_TRANSLATION_TABLE;

    private static final int DEFAULT_NUM_OF_SHIFTS = 4;

    static {
        TRANSLATION_TABLE = buildTranslationTable(TRANSLATE_FROM, TRANSLATE_TO);
        REVERSE_TRANSLATION_TABLE = buildTranslationTable(TRANSLATE_TO, TRANSLATE_FROM);
    }

    static String makeSecret(String plaintext, int count) {
        var base64Encoded = Base64.getEncoder().encode(plaintext.getBytes());
        var secret = String.format("2%s", new String(base64Encoded, StandardCharsets.US_ASCII));
        var random = new Random();
        var randomEncoders = ENCODERS.values();
        var encodersSize = randomEncoders.length;
        for (int i = 0; i < count; i++) {
            var encoder = randomEncoders[random.nextInt(encodersSize)];
            var encoded = encoder.function.apply(secret);
            secret = String.format("%s%s", encoder.ordinal() + 1, encoded);
        }
        return secret;
    }

    public static void main(String[] args) {
        var p = Paths.get("audio/Coldplay/album1");
        System.out.println(p);
        System.out.println(p.getRoot());
        System.out.println(p.subpath(0, 1));
    }

    static Result decryptSecret(String ciphertext) {
        return decryptSecret(ciphertext, -1);
    }

    private static Result decryptSecret(String ciphertext, int backTrackRounds) {
        var firstChar = ciphertext.charAt(0);
        if (firstChar > 48 && firstChar < 52) { // 1, 2, 3
            var stepId = Character.digit(firstChar, 10);
            stepId = stepId - 1;
            var decoder = DECODERS.values()[stepId];
            var decoded = decoder.function.apply(ciphertext.substring(1));
            return decryptSecret(decoded, backTrackRounds + 1);
        }
        return new Result(backTrackRounds, ciphertext);
    }

    static String step1(String secret) {
        return translate(secret, TRANSLATION_TABLE);
    }

    static String step2(String input) {
        return new String(Base64.getEncoder().encode(input.getBytes()), StandardCharsets.US_ASCII);
    }

    static String step3(String input) {
        var lowercaseAlpha = ASCII_LOWERCASE;
        var shifted = shiftString(lowercaseAlpha, DEFAULT_NUM_OF_SHIFTS);
        var translationTable = buildTranslationTable(lowercaseAlpha, shifted);
        return translate(input, translationTable);
    }

    private static Map<Character, Character> buildTranslationTable(String translateFrom, String translateTo) {
        Map<Character, Character> table = new LinkedHashMap<>(translateFrom.length());
        for (int i = 0; i < translateFrom.length(); i++) {
            table.put(translateFrom.charAt(i), translateTo.charAt(i));
        }
        return table;
    }

    private static String translate(String secret, Map<Character, Character> translationTable) {
        var translation = new char[secret.length()];
        for (int i = 0; i < secret.length(); i++) {
            var secretChar = secret.charAt(i);
            translation[i] = translationTable.getOrDefault(secretChar, secretChar);
        }
        return new String(translation);
    }

    private static String shiftString(String input, int shift) {
        int len = input.length();
        shift = shift % len; // ensure a shift value is within alphabet (26 chars)
        return IntStream.concat(IntStream.range(shift, len), IntStream.range(0, shift))
                .mapToObj(i -> String.valueOf(input.charAt(i)))
                .collect(Collectors.joining());
    }

    static String decodeStep1(String input) {
        return translate(input, REVERSE_TRANSLATION_TABLE);
    }

    static String decodeStep2(String input) {
        return new String(Base64.getDecoder().decode(input.getBytes()), StandardCharsets.US_ASCII);
    }

    static String decodeStep3(String input) {
        var lowercaseAlpha = ASCII_LOWERCASE;
        var shifted = shiftString(lowercaseAlpha, DEFAULT_NUM_OF_SHIFTS);
        var reversedTranslationTable = buildTranslationTable(shifted, lowercaseAlpha);
        return translate(input, reversedTranslationTable);
    }

    private enum ENCODERS {

        STEP_1(Lab11::step1),

        STEP_2(Lab11::step2),

        STEP_3(Lab11::step3);

        final Function<String, String> function;

        ENCODERS(Function<String, String> function) {
            this.function = function;
        }
    }

    private enum DECODERS {

        STEP_1(Lab11::decodeStep1),

        STEP_2(Lab11::decodeStep2),

        STEP_3(Lab11::decodeStep3);

        final Function<String, String> function;

        DECODERS(Function<String, String> function) {
            this.function = function;
        }
    }

    record Result(int rounds, String plaintext) {

    }
}
