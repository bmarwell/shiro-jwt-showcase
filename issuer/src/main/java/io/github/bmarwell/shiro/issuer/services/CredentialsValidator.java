package io.github.bmarwell.shiro.issuer.services;

import io.github.bmarwell.shiro.issuer.dto.LoginCredentials;
import java.io.Serializable;

public interface CredentialsValidator extends Serializable {

  void validate(LoginCredentials credentials);
}
