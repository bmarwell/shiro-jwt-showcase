/*
 * Copyright (C) 2022 Benjamin Marwell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.bmarwell.shiro.jwt.service;

import static java.util.Objects.requireNonNull;

import io.github.bmarwell.shiro.jwt.json.JsonbJwtDeserializer;
import io.github.bmarwell.shiro.keystore.KeystoreLoader;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class KeyService implements Serializable {

  private static final char[] KEY_PASSWORD = "changeit".toCharArray();

  @Inject
  private JsonbJwtDeserializer jsonbJwtDeserializer;

  @Inject
  @ConfigProperty(name="issuer.name")
  private String issuerName;

  public KeyStore getTrustStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
    final KeyStore keyStore = new KeystoreLoader().loadTruststore();
    return requireNonNull(keyStore);
  }

  @Produces
  @Dependent
  public JwtParser createJwtBuilder() {
    return Jwts.parserBuilder()
        .deserializeJsonWith(jsonbJwtDeserializer)
        .setSigningKey(getSigningCertificate().getPublicKey())
        // see: TokenServiceImpl.java
        .requireAudience("shiro-jwt")
        .requireIssuer(issuerName)
        .build();
  }

  private Certificate getSigningCertificate() {
    try {
      final Certificate issuer = getTrustStore().getCertificate("issuer");

      return requireNonNull(issuer);
    } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException javaSecurityKeyStoreException) {
      throw new IllegalStateException(javaSecurityKeyStoreException);
    } catch (IOException javaIoIOException) {
      throw new UncheckedIOException(javaIoIOException);
    }
  }
}
