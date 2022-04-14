package io.github.bmarwell.shiro.jwt.shiro;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.shiro.authc.HostAuthenticationToken;

public class ShiroJsonWebToken implements HostAuthenticationToken {

  private final String host;
  private final String subject;
  private final Jws<Claims> jsonWebToken;

  public ShiroJsonWebToken(Jws<Claims> jsonWebToken) {
    this.host = jsonWebToken.getBody().getIssuer();
    this.subject = jsonWebToken.getBody().getSubject();
    this.jsonWebToken = jsonWebToken;
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
}
