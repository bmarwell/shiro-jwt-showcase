package io.github.bmarwell.shiro.keystore;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class KeystoreLoader {

  private static final String KEY_STORE_TYPE = "PKCS12";
  private static final char[] KEY_STORE_PASSWORD = "changeit".toCharArray();

  public KeyStore loadKeystore()
      throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
    KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);

    try (InputStream fis = getClass().getResourceAsStream("/io/github/bmarwell/shiro/keystore/keystore.p12")) {
      requireNonNull(fis);
      keyStore.load(fis, KEY_STORE_PASSWORD);
    }

    return requireNonNull(keyStore);
  }

  public KeyStore loadTruststore()
      throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
    KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);

    try (InputStream fis = getClass().getResourceAsStream("/io/github/bmarwell/shiro/keystore/truststore.p12")) {
      requireNonNull(fis);
      keyStore.load(fis, KEY_STORE_PASSWORD);
    }

    return requireNonNull(keyStore);
  }

}
