package com.enzo.fxapp;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClientUtil {

    public static String sendPostRequest(String urlString, String username, String password) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String data = "username=" + username + "&password=" + password;
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } else {
            return new String(conn.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
