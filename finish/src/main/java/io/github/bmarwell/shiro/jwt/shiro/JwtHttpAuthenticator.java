package io.github.bmarwell.shiro.jwt.shiro;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.MalformedJwtException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;

public class JwtHttpAuthenticator extends BearerHttpAuthenticationFilter {

  private static final Logger LOG = Logger.getLogger(JwtHttpAuthenticator.class.getName());

  private final Instance<JwtParser> jwtParser;

  public JwtHttpAuthenticator() {
    super();
    this.jwtParser = CDI.current().select(JwtParser.class);
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
      final Jws<Claims> jws = jwtParser.get().parseClaimsJws(principalsAndCredentials[0]);

      return new ShiroJsonWebToken(jws, principalsAndCredentials[0]);
    } catch (MalformedJwtException jwtEx) {
      throw new WebApplicationException("Invalid JWT.", jwtEx, Status.BAD_REQUEST);
    }
  }

  @Override
  protected AuthenticationToken createBearerToken(String token, ServletRequest request) {
    throw new UnsupportedOperationException(
        "not yet implemented: [io.github.bmarwell.shiro.jwt.shiro.JwtHttpAuthenticator::createBearerToken].");
  }
}