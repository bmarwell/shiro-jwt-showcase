package io.github.bmarwell.shiro.issuer.services;

import io.github.bmarwell.shiro.issuer.dto.LoginCredentials;
import java.io.Serializable;
import java.util.List;

public interface TokenService extends Serializable {

  String createJwt(LoginCredentials credentials, List<String> roles);
}
