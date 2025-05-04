package tud.b00168512.cryptohack.hash;

public class JackBirthday {

    public static void main(String[] args) {
        var H = Math.pow(2, 11);
        var result = Math.log(0.5) / Math.log(1 - (1 / H));
        System.out.println(result);
        // Result: 1419.218823985369
        // Accepted answer would be 1420
    }
}
