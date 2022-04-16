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
