package io.github.bmarwell.shiro.jwt.shiro;

import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class JwtRealm extends AuthorizingRealm {

  private static final ThreadLocal<ShiroJsonWebToken> jwtThreadToken = new ThreadLocal<>();

  public JwtRealm() {
    //
  }

  @Override
  public String getName() {
    return "jwt";
  }

  @Override
  public Class<?> getAuthenticationTokenClass() {
    return ShiroJsonWebToken.class;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    ShiroJsonWebToken jwt = (ShiroJsonWebToken) token;
    jwtThreadToken.set(jwt);

    return new SimpleAuthenticationInfo(jwt.getPrincipal(), jwt, getName());
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    final ShiroJsonWebToken jsonWebToken = jwtThreadToken.get();
    final Claims claims = jsonWebToken.getCredentials().getBody();
    final List<String> roles = Optional.ofNullable(claims.get("roles", List.class)).orElse(List.<String>of());
    jwtThreadToken.remove();

    return new SimpleAuthorizationInfo(Set.copyOf(roles));
  }

}
