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
