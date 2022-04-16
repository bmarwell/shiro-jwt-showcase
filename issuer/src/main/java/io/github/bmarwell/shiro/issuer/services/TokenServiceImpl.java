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

import io.github.bmarwell.shiro.issuer.dto.LoginCredentials;
import io.jsonwebtoken.JwtBuilder;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Default
@ApplicationScoped
public class TokenServiceImpl implements TokenService {

  @Inject
  private JwtBuilder jwtBuilder;

  @Inject
  @ConfigProperty(name="issuer.name")
  String issuerName;

  @Override
  public String createJwt(LoginCredentials credentials, List<String> roles) {
    final java.util.Date from = Date.from(Instant.now());
    final java.util.Date expires = Date.from(Instant.now().plusSeconds(60_000L));

    return jwtBuilder
        .setIssuer(issuerName)
        .setSubject(credentials.getUsername())
        .setIssuedAt(from)
        .setNotBefore(from)
        .setExpiration(expires)
        .setAudience("shiro-jwt")
        .claim("roles", roles)
        .compact();
  }
}
