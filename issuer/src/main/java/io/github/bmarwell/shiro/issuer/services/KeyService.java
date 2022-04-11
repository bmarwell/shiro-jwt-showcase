package io.github.bmarwell.shiro.issuer.services;

import static java.util.Objects.requireNonNull;

import io.github.bmarwell.shiro.issuer.json.JsonbSerializer;
import io.github.bmarwell.shiro.keystore.KeystoreLoader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class KeyService implements Serializable {

  private static final char[] KEY_PASSWORD = "changeit".toCharArray();

  @Inject
  private JsonbSerializer jsonbSerializer;

  public KeyStore createKeyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
    final KeyStore keyStore = new KeystoreLoader().loadKeystore();
    return requireNonNull(keyStore);
  }

  @Produces
  @Dependent
  public JwtBuilder createJwtBuilder()
      throws CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
    final Key signerKey = createKeyStore().getKey("issuer", KEY_PASSWORD);

    return Jwts.builder()
        .serializeToJsonWith(jsonbSerializer)
        .signWith(requireNonNull(signerKey), SignatureAlgorithm.ES256);
  }
}
