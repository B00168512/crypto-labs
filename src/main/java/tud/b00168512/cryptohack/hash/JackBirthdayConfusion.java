package tud.b00168512.cryptohack.hash;

public class JackBirthdayConfusion {

    public static void main(String[] args) {
        var p = 0.75;
        var H = Math.pow(2, 11);

        var result = Math.sqrt(2 * H * Math.log(1 / (1 - p)));
        System.out.println(result);
        // Result: 75.35424144099038
        // Accepted answer: 76
    }
}
