package com.github.aurae.trustme;

import android.content.Context;
import android.support.annotation.RawRes;

import javax.net.ssl.SSLSocketFactory;

/**
 * Creates an SSLSocketFactory instance for use with a custom CA, which would otherwise be considered "not trustworthy".
 * This can be fed into HttpsURLConnection, as well as networking libraries such as OkHttp's OkHttpClient.
 */
public final class TrustMeAndroid extends TrustMe {

    private TrustMeAndroid() {
        super();
    }

    /**
     * Creates an SSLSocketFactory instance for use with the CA provided in the given File.
     *
     * @param context   Context used to open up the CA file
     * @param caRawFile Raw resource file to the CA (in .crt or .cer format, for instance)
     * @return An SSLSocketFactory which trusts the provided CA when provided to network clients
     */
    public static SSLSocketFactory newSSLSocketFactory(Context context, @RawRes int caRawFile) {
        return SSLSocketFactoryCreator.create(context.getResources().openRawResource(caRawFile));
    }
}
