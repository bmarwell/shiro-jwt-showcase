package io.github.bmarwell.shiro.jwt.shiro;

import io.github.bmarwell.shiro.jwt.json.JsonbJwtDeserializer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class JwtHttpAuthenticator extends BearerHttpAuthenticationFilter {

  private static final Logger LOG = Logger.getLogger(JwtHttpAuthenticator.class.getName());

  private final Instance<JsonbJwtDeserializer> jsonbJwtDeserializer;

  private final String issuerName;


  public JwtHttpAuthenticator() {
    super();
    this.jsonbJwtDeserializer = CDI.current().select(JsonbJwtDeserializer.class);
    Config config = ConfigProvider.getConfig();
    this.issuerName = config.getValue("issuer.name", String.class);
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

    final JwtParser jwtParser = Jwts.parserBuilder()
        //.setSigningKey((Key) null)
        .deserializeJsonWith(jsonbJwtDeserializer.get())
        // see: TokenServiceImpl.java
        .requireAudience("shiro-jwt")
        .requireIssuer(issuerName)
        .build();

    final Jwt<Header<?>, Claims> jwt = jwtParser.parse(principalsAndCredentials[0]);

    return new ShiroJsonWebToken(jwt);
  }

  @Override
  protected AuthenticationToken createBearerToken(String token, ServletRequest request) {
    throw new UnsupportedOperationException(
        "not yet implemented: [io.github.bmarwell.shiro.jwt.shiro.JwtHttpAuthenticator::createBearerToken].");
  }
}
