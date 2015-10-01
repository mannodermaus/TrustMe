package com.github.aurae.trustme;

import javax.net.ssl.SSLSocketFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Creates an SSLSocketFactory instance for use with a custom CA, which would otherwise be considered "not trustworthy".
 * This can be fed into HttpsURLConnection, as well as networking libraries such as OkHttp's OkHttpClient.
 */
public class TrustMe {

    protected TrustMe() {
        throw new AssertionError();
    }

    /**
     * Creates an SSLSocketFactory instance for use with the CA provided in the given File.
     *
     * @param caFile  File to the CA (in .crt or .cer format, for instance)
     * @return An SSLSocketFactory which trusts the provided CA when provided to network clients
     */
    public static SSLSocketFactory newSSLSocketFactory(File caFile) {
        try {
            return SSLSocketFactoryCreator.create(new FileInputStream(caFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an SSLSocketFactory instance for use with the CA provided in the given byte array.
     *
     * @param caBytes  Byte array making up the CA (in .crt or .cer format, for instance)
     * @return An SSLSocketFactory which trusts the provided CA when provided to network clients
     */
    public static SSLSocketFactory newSSLSocketFactory(byte[] caBytes) {
        return SSLSocketFactoryCreator.create(new ByteArrayInputStream(caBytes));
    }
}
