package io.github.bmarwell.shiro.issuer.services;

import io.github.bmarwell.shiro.issuer.json.JsonbSerializer;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class KeyService implements Serializable {

  private static final String KEY_STORE_TYPE = "PKCS12";
  private static final char[] KEY_STORE_PASSWORD = "changeit".toCharArray();

  @Inject
  private JsonbSerializer jsonbSerializer;

  @Inject
  @ConfigProperty(name="keystore.path")
  File keystorePath;

  public KeyStore createKeyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
    KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);

    try (InputStream fis = Files.newInputStream(keystorePath.toPath(), StandardOpenOption.READ)) {
      keyStore.load(fis, KEY_STORE_PASSWORD);
    }

    return keyStore;
  }

  @Produces
  @ApplicationScoped
  public JwtBuilder createJwtBuilder()
      throws CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
    final Key signerKey = createKeyStore().getKey("issuer", "changeit".toCharArray());

    return Jwts.builder()
        .serializeToJsonWith(jsonbSerializer)
        .signWith(signerKey, SignatureAlgorithm.ES256);
  }
}
