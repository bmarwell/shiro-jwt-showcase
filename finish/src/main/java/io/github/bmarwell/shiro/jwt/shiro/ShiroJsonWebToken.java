package io.github.bmarwell.shiro.jwt.shiro;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.shiro.authc.HostAuthenticationToken;

public class ShiroJsonWebToken implements HostAuthenticationToken {

  private final String host;
  private final String subject;
  private final Jws<Claims> jsonWebToken;

  private final String rawToken;

  public ShiroJsonWebToken(Jws<Claims> jsonWebToken, String rawToken) {
    this.host = jsonWebToken.getBody().getIssuer();
    this.subject = jsonWebToken.getBody().getSubject();
    this.jsonWebToken = jsonWebToken;
    this.rawToken = rawToken;
  }

  @Override
  public String getHost() {
    return this.host;
  }

  @Override
  public Object getPrincipal() {
    return this.subject;
  }

  @Override
  public Jws<Claims> getCredentials() {
    return this.jsonWebToken;
  }

  public String getRawToken() {
    return rawToken;
  }
}
