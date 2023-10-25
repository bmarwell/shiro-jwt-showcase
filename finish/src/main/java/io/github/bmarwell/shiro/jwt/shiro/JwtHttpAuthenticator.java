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
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;

public class JwtHttpAuthenticator extends BearerHttpAuthenticationFilter implements AutoCloseable {

    private static final Logger LOG = Logger.getLogger(JwtHttpAuthenticator.class.getName());

    private final KeyService keyService;

    public JwtHttpAuthenticator() {
        super();
        this.keyService = new KeyService();
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String authorizationHeaderContent = getAuthzHeader(request);
        if (authorizationHeaderContent == null || authorizationHeaderContent.isBlank()) {
            // Create an empty authentication token since there is no
            // Authorization header.
            return createBearerToken("", request);
        }

        LOG.log(Level.FINER, "Attempting to execute login with auth header");
        final String[] principalsAndCredentials = getPrincipalsAndCredentials(authorizationHeaderContent, request);

        // TODO: the verifying should instead (only?) be done in a credentials matcher.
        try {
            final Jws<Claims> jws = keyService.createJwtParser().parseSignedClaims(principalsAndCredentials[0]);

            return new ShiroJsonWebToken(jws, principalsAndCredentials[0], true);
        } catch (MalformedJwtException | SignatureException jwtEx) {
            LOG.log(Level.WARNING, jwtEx, () -> "Invalid JWT: " + principalsAndCredentials[0]);
            return createBearerToken("", request);
        }
    }

    @Override
    public void close() {
        this.keyService.close();
    }
}
