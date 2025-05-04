package tud.b00168512.cryptohack.general;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class FindCertmodulus {

    // 22825373692019530804306212864609512775374171823993708516509897631547513634635856375624003737068034549047677999310941837454378829351398302382629658264078775456838626207507725494030600516872852306191255492926495965536379271875310457319107936020730050476235278671528265817571433919561175665096171189758406136453987966255236963782666066962654678464950075923060327358691356632908606498231755963567382339010985222623205586923466405809217426670333410014429905146941652293366212903733630083016398810887356019977409467374742266276267137547021576874204809506045914964491063393800499167416471949021995447722415959979785959569497
    public static void main(String[] args) throws Exception {

        var file = PrivacyEnhancedMail.class.getResource("/cryptohack/2048b-rsa-example-cert.der").toURI();
        var bytes = Files.readAllBytes(Paths.get(file));
        String derCertificateBase64 = "MII...veryLongString...AAA=";
//        byte[] derEncodedCertificate = Base64.getDecoder().decode(derCertificateBase64);

        try {
            BigInteger modulus = getModulusFromDER(bytes);
            System.out.println("Modulus (decimal): " + modulus.toString(10));
        } catch (CertificateException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static BigInteger getModulusFromDER(byte[] derEncodedCertificate) throws CertificateException {
        try (InputStream is = new ByteArrayInputStream(derEncodedCertificate)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
            PublicKey publicKey = cert.getPublicKey();

            if (publicKey instanceof RSAPublicKey) {
                RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
                return rsaPublicKey.getModulus();
            } else {
                throw new CertificateException("Certificate does not contain an RSA public key.");
            }
        } catch (IOException e) {
            throw new CertificateException("Error reading DER encoded certificate: " + e.getMessage(), e);
        }
    }

    /**
     * Helper method to convert a DER certificate from a Base64 string.
     * @param base64EncodedDer Base64 encoded DER certificate.
     * @return The DER encoded certificate as a byte array, or null on error.
     */
    public static byte[] derFromBase64(String base64EncodedDer) {
        try{
            return Base64.getDecoder().decode(base64EncodedDer);
        } catch (IllegalArgumentException e){
            System.err.println("Invalid Base64 encoding: " + e.getMessage());
            return null;
        }

    }
}
