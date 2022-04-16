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

package io.github.bmarwell.shiro.jwt.shiro;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import javax.enterprise.inject.spi.CDI;
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

    final JwtParser jwtParser = CDI.current().select(JwtParser.class).get();
    if (!jwtParser.isSigned(credentials.getRawToken())) {
      return false;
    }

    final Jws<Claims> reParsedJws = jwtParser.parseClaimsJws(credentials.getRawToken());

    return reParsedJws.getHeader().equals(jws.getHeader())
        && reParsedJws.getBody().equals(jws.getBody())
        && reParsedJws.getSignature().equals(jws.getSignature());
  }
}
