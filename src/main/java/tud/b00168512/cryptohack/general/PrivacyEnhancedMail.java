package tud.b00168512.cryptohack.general;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class PrivacyEnhancedMail {

    // 15682700288056331364787171045819973654991149949197959929860861228180021707316851924456205543665565810892674190059831330231436970914474774562714945620519144389785158908994181951348846017432506464163564960993784254153395406799101314760033445065193429592512349952020982932218524462341002102063435489318813316464511621736943938440710470694912336237680219746204595128959161800595216366237538296447335375818871952520026993102148328897083547184286493241191505953601668858941129790966909236941127851370202421135897091086763569884760099112291072056970636380417349019579768748054760104838790424708988260443926906673795975104689
    public static void main(String[] args) {
        String fileName = "/cryptohack/privacy_enhanced_mail.pem";
        var file = PrivacyEnhancedMail.class.getResource(fileName).getFile();
        String begin = "-----BEGIN RSA PRIVATE KEY-----";
        String end = "-----END RSA PRIVATE KEY-----";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder fileContentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContentBuilder.append(line).append("\n");
            }
            String fileContent = fileContentBuilder.toString();

            int startPos = fileContent.indexOf(begin);
            int endPos = fileContent.indexOf(end);

            if (startPos == -1 || endPos == -1) {
                System.out.println("Nieprawidłowy klucz");
                return;
            }

            startPos += begin.length();
            String privKeyBase64 = fileContent.substring(startPos, endPos).replaceAll("\\s+", ""); // Remove whitespaces

            byte[] keyDecoded = Base64.getDecoder().decode(privKeyBase64);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyDecoded);
            java.security.interfaces.RSAPrivateKey privateKey = (java.security.interfaces.RSAPrivateKey) keyFactory.generatePrivate(keySpec);

            System.out.println(privateKey.getPrivateExponent());

        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
