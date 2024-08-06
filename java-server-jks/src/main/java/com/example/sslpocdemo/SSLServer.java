package com.example.sslpocdemo;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

public class SSLServer {
    public static void main(String[] args) throws Exception {
        // Load keystore
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(SSLServer.class.getResourceAsStream("/mykeystore.jks"), "changeit".toCharArray());

        // Initialize key manager factory with the keystore
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, "keypassword".toCharArray());

        // Initialize SSL context with key managers
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);

        // Create an HTTPS server
        HttpsServer server = HttpsServer.create(new InetSocketAddress(8443), 0);
        server.setHttpsConfigurator(new HttpsConfigurator(sslContext));
        server.createContext("/", exchange -> {
            String response = "Hello, SSL!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8443");
    }
}
