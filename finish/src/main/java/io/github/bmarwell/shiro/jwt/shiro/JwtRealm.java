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

    return new SimpleAuthorizationInfo(Set.copyOf(roles));
  }

}
