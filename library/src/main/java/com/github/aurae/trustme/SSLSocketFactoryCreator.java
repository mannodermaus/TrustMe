package com.github.aurae.trustme;

import java.io.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

final class SSLSocketFactoryCreator {

    private SSLSocketFactoryCreator() {
        throw new AssertionError();
    }

    static SSLSocketFactory create(InputStream caInputStream) {
        try {
            // Generate the CA Certificate from the raw resource file
            Certificate ca = CertificateFactory.getInstance("X.509").generateCertificate(caInputStream);

            // Load the key store using the CA
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Initialize the TrustManager with this CA
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            // Create an SSL context that uses the created trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception ex) {
            throw new RuntimeException(ex);

        } finally {
            if (caInputStream != null) {
                try {
                    caInputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
