package io.github.bmarwell.shiro.issuer;

import io.github.bmarwell.shiro.issuer.dto.LoginCredentials;
import io.github.bmarwell.shiro.issuer.services.CredentialsValidator;
import io.github.bmarwell.shiro.issuer.services.TokenService;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IssueEndpoint {

  /**
   * In a real app, you would probably use CDI to inject a service from elsewhere (not the same jar).
   */
  @Inject
  private CredentialsValidator validator;

  @Inject
  private TokenService tokenService;

  public IssueEndpoint() {
    // cdi
  }

  @POST
  @Path("/login")
  public Response doLogin(LoginCredentials credentials) {
    validator.validate(credentials);
    String jwt = tokenService.createJwt(credentials);

    return Response.accepted()
        .entity(Map.of("token", jwt))
        .build();
  }

}
