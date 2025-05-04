package tud.b00168512.cryptohack.diffieHellman;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Implements decrypt.py
 */
public class DerivingSymmetricKeys {

    public static void main(String[] args) {
        var g = 2;
        var p = new BigInteger("2410312426921032588552076022197566074856950548502459942654116941958108831682612228890093858261341614673227141477904012196503648957050582631942730706805009223062734745341073406696246014589361659774041027169249453200378729434170325843778659198143763193776859869524088940195577346119843545301547043747207749969763750084308926339295559968882457872412993810129130294592999947926365264059284647209730384947211681434464714438488520940127459844288859336526896320919633919");
        var A = new BigInteger("112218739139542908880564359534373424013016249772931962692237907571990334483528877513809272625610512061159061737608547288558662879685086684299624481742865016924065000555267977830144740364467977206555914781236397216033805882207640219686011643468275165718132888489024688846101943642459655423609111976363316080620471928236879737944217503462265615774774318986375878440978819238346077908864116156831874695817477772477121232820827728424890845769152726027520772901423784");
        var b = new BigInteger("197395083814907028991785772714920885908249341925650951555219049411298436217190605190824934787336279228785809783531814507661385111220639329358048196339626065676869119737979175531770768861808581110311903548567424039264485661330995221907803300824165469977099494284722831845653985392791480264712091293580274947132480402319812110462641143884577706335859190668240694680261160210609506891842793868297672619625924001403035676872189455767944077542198064499486164431451944");
        var B = new BigInteger("1241972460522075344783337556660700537760331108332735677863862813666578639518899293226399921252049655031563612905395145236854443334774555982204857895716383215705498970395379526698761468932147200650513626028263449605755661189525521343142979265044068409405667549241125597387173006460145379759986272191990675988873894208956851773331039747840312455221354589910726982819203421992729738296452820365553759182547255998984882158393688119629609067647494762616719047466973581");

        var iv = new BigInteger("737561146ff8194f45290f5766ed6aba", 16);
        var encryptedFlag = new BigInteger("39c99bf2f0c14678d6a5416faef954b5893c316fc3c48622ba1fd6a9fe85f3dc72a29c394cf4bc8aff6a7b21cae8e12c", 16);

        var sharedSecret = A.modPow(b, p);

        var plaintext = decryptFlag(sharedSecret, iv.toByteArray(), encryptedFlag.toByteArray());

        System.out.println(plaintext);
    }

    public static String decryptFlag(BigInteger sharedSecret, byte[] iv, byte[] ciphertext) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] key = Arrays.copyOf(sha1.digest(sharedSecret.toString().getBytes(StandardCharsets.US_ASCII)), 16);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] plaintext = cipher.doFinal(ciphertext);

            // Remove padding if valid
            if (isPkcs7Padded(plaintext)) {
                int padLength = plaintext[plaintext.length - 1];
                return new String(Arrays.copyOfRange(plaintext, 0, plaintext.length - padLength), StandardCharsets.US_ASCII);
            } else {
                return new String(plaintext, StandardCharsets.US_ASCII);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isPkcs7Padded(byte[] message) {
        int paddingLength = message[message.length - 1] & 0xFF; // convert to unsigned
        if (paddingLength <= 0 || paddingLength > 16) return false;
        for (int i = message.length - paddingLength; i < message.length; i++) {
            if ((message[i] & 0xFF) != paddingLength) {
                return false;
            }
        }
        return true;
    }



}
