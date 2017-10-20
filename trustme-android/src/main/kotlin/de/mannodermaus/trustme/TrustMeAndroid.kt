package de.mannodermaus.trustme

import android.content.Context
import android.support.annotation.RawRes
import javax.net.ssl.SSLSocketFactory

/**
 * Creates an SSLSocketFactory instance for use with a custom CA,
 * which would otherwise be considered "not trustworthy".
 *
 * This can be fed into HttpsURLConnection,
 * as well as networking libraries such as OkHttp.
 */
class TrustMeAndroid private constructor() {

  companion object {
    /**
     * Creates an SSLSocketFactory instance for use with the CA provided in the given File.
     *
     * @param context Context used to open up the CA file
     * @param id Raw resource file to the CA (in .crt or .cer format, for instance)
     * @return An SSLSocketFactory which trusts the CA when provided to network clients
     */
    @JvmStatic
    fun resource(context: Context, @RawRes id: Int): SSLSocketFactory =
        TrustMe.inputStream(context.resources.openRawResource(id))
  }
}
