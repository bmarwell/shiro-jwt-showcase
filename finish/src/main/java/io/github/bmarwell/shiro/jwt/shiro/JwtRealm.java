package io.github.bmarwell.shiro.jwt.shiro;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import java.util.Optional;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class JwtRealm extends AuthorizingRealm {

  private static final ThreadLocal<Jwt<Header<?>, Claims>> jwtThreadToken = new ThreadLocal<>();

  public JwtRealm() {
    //
  }

  @Override
  public Class<?> getAuthenticationTokenClass() {
    return ShiroJsonWebToken.class;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    Jws jwt = (Jws) token;
    jwtThreadToken.set(jwt);

    throw new UnsupportedOperationException(
        "not yet implemented: [io.github.bmarwell.shiro.jwt.shiro.JwtRealm::doGetAuthorizationInfo].");
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    final Jwt<Header<?>, Claims> jsonWebToken = jwtThreadToken.get();
    final Optional<Object> roles = Optional.ofNullable(jsonWebToken.getBody().get("roles"));
    jwtThreadToken.remove();

    throw new UnsupportedOperationException(
        "not yet implemented: [io.github.bmarwell.shiro.jwt.shiro.JwtRealm::doGetAuthorizationInfo].");
  }
}
