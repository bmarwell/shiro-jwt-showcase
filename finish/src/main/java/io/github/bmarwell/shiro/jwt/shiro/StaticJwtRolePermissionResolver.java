/*
 * Copyright (C) 2022 The shiro-jjwt-showcase team
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

import java.util.Collection;
import java.util.List;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

public class StaticJwtRolePermissionResolver implements RolePermissionResolver {

  @Override
  public Collection<Permission> resolvePermissionsInRole(String roleString) {
    return switch (roleString) {
      case "admin" -> List.of(new WildcardPermission("troopers:*"));
      case "mod" -> List.of(
          new WildcardPermission("troopers:read"),
          new WildcardPermission("troopers:create"),
          new WildcardPermission("troopers:update")
          //, ... but not delete.
      );
      case "user" -> List.of(new WildcardPermission("troopers:read"));
      default -> List.of();
    };
  }
}
