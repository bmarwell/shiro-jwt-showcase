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
