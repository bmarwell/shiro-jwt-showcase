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

package io.github.bmarwell.shiro.issuer.services;

import static java.util.Objects.requireNonNull;

import io.github.bmarwell.shiro.issuer.json.JsonbSerializer;
import io.github.bmarwell.shiro.keystore.KeystoreLoader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
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
        .json(jsonbSerializer)
        .signWith(signerKey);
  }
}
