package tud.b00168512.cryptohack.rsa;

import java.math.BigInteger;

public class RsaDecryption {

    public static void main(String[] args) {
        var ciphertext = new BigInteger("77578995801157823671636298847186723593814843845525223303932");
        var n = new BigInteger("882564595536224140639625987659416029426239230804614613279163");
        var res = ciphertext.modPow(PrivateKeys.PRIVATE_KEY, n);
        System.out.println(res); // 13371337
    }
}
