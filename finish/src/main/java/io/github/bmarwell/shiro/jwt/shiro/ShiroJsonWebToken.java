package io.github.bmarwell.shiro.jwt.shiro;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import org.apache.shiro.authc.HostAuthenticationToken;

public class ShiroJsonWebToken implements HostAuthenticationToken {

  private final String host;
  private final String subject;
  private final Jwt jsonWebToken;

  public ShiroJsonWebToken(Jwt<Header<?>, Claims> jsonWebToken) {
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
  public Object getCredentials() {
    return this.jsonWebToken;
  }
}
