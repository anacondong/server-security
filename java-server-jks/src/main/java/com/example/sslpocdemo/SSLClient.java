package com.example.sslpocdemo;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;

public class SSLClient {
    public static void main(String[] args) throws Exception {
        // Load truststore
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(SSLClient.class.getResourceAsStream("/mytruststore.jks"), "changeit".toCharArray());

        // Initialize trust manager factory with the truststore
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        // Initialize SSL context with trust managers
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        // Set SSL context for HTTPS connections
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        // Make an HTTPS request
        URL url = new URL("https://localhost:8443/");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        System.out.println("Response: " + content.toString());
    }
}
