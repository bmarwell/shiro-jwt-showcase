/*
 * Copyright (C) 2022-2023 The shiro-jjwt-showcase team
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
package io.github.bmarwell.shiro.jwt.shiro;

import io.github.bmarwell.shiro.jwt.service.KeyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import java.util.Arrays;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class JwtCheckingCredentialsMatcher implements CredentialsMatcher {

  @Override
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    final ShiroJsonWebToken credentials = (ShiroJsonWebToken) info.getCredentials();
    final Jws<Claims> jws = credentials.getCredentials();
    final Object principal = token.getPrincipal();

    if (!principal.equals(credentials.getPrincipal())) {
      return false;
    }

    try (KeyService ks = new KeyService()) {
      final JwtParser jwtParser = ks.createJwtParser();

      if (!credentials.isValidated() && !jwtParser.isSigned(credentials.getRawToken())) {
        return false;
      }

      final Jws<Claims> reParsedJws = jwtParser.parseSignedClaims(credentials.getRawToken());

      return reParsedJws.getHeader().equals(jws.getHeader())
          && reParsedJws.getPayload().equals(jws.getPayload())
          && Arrays.equals(reParsedJws.getDigest(), jws.getDigest());
    }
  }
}
