package de.mannodermaus.trustme

import java.io.File
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

/**
 * Creates an SSLSocketFactory instance for use with a custom CA,
 * which would otherwise be considered "not trustworthy".
 *
 * This can be fed into HttpsURLConnection,
 * as well as networking libraries such as OkHttp.
 */
class TrustMe {

  companion object {
    /**
     * Creates an SSLSocketFactory instance for use with the CA provided in the given File.
     *
     * @param caFile File to the CA (in .crt or .cer format, for instance)
     * @return An SSLSocketFactory which trusts the CA when provided to network clients
     */
    @JvmStatic
    fun file(caFile: File): SSLSocketFactory = inputStream(caFile.inputStream())

    /**
     * Creates an SSLSocketFactory instance for use with the CA provided in the given byte array.
     *
     * @param caBytes Byte array making up the CA (in .crt or .cer format, for instance)
     * @return An SSLSocketFactory which trusts the CA when provided to network clients
     */
    @JvmStatic
    fun bytes(caBytes: ByteArray): SSLSocketFactory = inputStream(caBytes.inputStream())

    /**
     * Creates an SSLSocketFactory instance from the given InputStream.
     * Will propagate any Exceptions to the caller.
     *
     * @param caInputStream The stream to construct the factory from
     * @return An SSLSocketFactory which trust the CA when provided to network clients
     */
    @JvmStatic
    fun inputStream(caInputStream: InputStream): SSLSocketFactory =
        caInputStream.use {
          // Generate the CA Certificate from the raw resource file
          val ca = CertificateFactory.getInstance("X.509").generateCertificate(it)

          // Load the key store using the CA
          val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
          keyStore.load(null, null)
          keyStore.setCertificateEntry("ca", ca)

          // Initialize the TrustManager with this CA
          val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
          tmf.init(keyStore)

          // Create an SSL context that uses the created trust manager
          val sslContext = SSLContext.getInstance("TLS")
          sslContext.init(null, tmf.trustManagers, SecureRandom())

          return sslContext.socketFactory
        }
  }
}
