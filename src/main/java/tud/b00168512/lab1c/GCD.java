package tud.b00168512.lab1c;

public class GCD {

    public static int gcd(int a, int b) {
        while (b!= 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        int num1 = 4105;
        int num2 = 10;
        int gcd1 = gcd(num1, num2);
        System.out.println("GCD of " + num1 + " and " + num2 + " is: " + gcd1);

        num1 = 4539;
        num2 = 6;
        int gcd2 = gcd(num1, num2);
        System.out.println("GCD of " + num1 + " and " + num2 + " is: " + gcd2);

    }
}
