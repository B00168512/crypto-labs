package tud.b00168512.cryptohack.symmetric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class EcbOracle {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String URL = "http://aes.cryptohack.org/ecb_oracle/encrypt/";
    private static final String ALPAHANUMERIC_CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_{}";

    public static void main(String[] args) {
        StringBuilder flag = new StringBuilder();
        String input = "A".repeat(32);

        int i = 0;
        while (i <= 32) {
            String shortInput = input.substring(0, input.length() - 1);

            String refResponse = sendRequest(shortInput);
            if (refResponse == null) break;

            String refBlock = refResponse.substring(0, 64);

            for (char c : ALPAHANUMERIC_CHAR_LIST.toCharArray()) {
                String testInput = shortInput + flag.toString() + c;
                String testResponse = sendRequest(testInput);
                if (testResponse != null && testResponse.substring(0, 64).equals(refBlock)) {
                    flag.append(c);
                    System.out.print("\r" + flag); // Print char by char until the flag is done
                    break;
                }
            }

            input = shortInput;
            i++;
        }
    }

    private static String sendRequest(String input) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String hexInput = new BigInteger(input.getBytes(StandardCharsets.UTF_8)).toString(16);
            HttpGet request = new HttpGet(URL + hexInput);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getCode() != 200) {
                    throw new HttpResponseException(response.getCode(), response.getReasonPhrase());
                }

                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);

                JsonNode json = OBJECT_MAPPER.readTree(responseString);
                return json.get("ciphertext").asText();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
