package tud.b00168512.cryptohack.web;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HelloJose {

    public static void main(String[] args) {
        var token = "{\"username\":\"JOSE\",\"admin\":true}"; //{"typ":"JWT","alg":"none"}
        var encoded = Base64.getEncoder().encode(token.getBytes());
        System.out.println(new String(encoded));

        var t = "eyJ0eXAiOiJKV1QiLCJhbGciOiJub25lIn0.eyJ1c2VybmFtZSI6IkpPU0UiLCJhZG1pbiI6dHJ1ZX0.2lTWIV5g7Vu3XN3IEYuc2bWjCIf2kdT0hZK7gpS5Cu0";
        System.out.println(new String(Base64.getDecoder().decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJub25lIn0")));
        System.out.println(new String(Base64.getDecoder().decode("eyJ1c2VybmFtZSI6IkpPU0UiLCJhZG1pbiI6dHJ1ZX0")));
        System.out.println(new String(Base64.getDecoder().decode("2lTWIV5g7Vu3XN3IEYuc2bWjCIf2kdT0hZK7gpS5Cu0"), StandardCharsets.UTF_8));
    }
}
