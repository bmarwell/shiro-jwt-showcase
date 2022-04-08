package io.github.bmarwell.shiro.issuer.services;

import io.github.bmarwell.shiro.issuer.dto.LoginCredentials;
import java.util.Arrays;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

@Default
@ApplicationScoped
public class CredentialsValidatorImpl implements CredentialsValidator {

  @Override
  public void validate(LoginCredentials credentials) {
    // all are ok if user == password
    if (!Arrays.equals(credentials.getUsername().toCharArray(), credentials.getPassword())) {
      throw new WebApplicationException("No details given", Status.UNAUTHORIZED);
    }
  }
}
