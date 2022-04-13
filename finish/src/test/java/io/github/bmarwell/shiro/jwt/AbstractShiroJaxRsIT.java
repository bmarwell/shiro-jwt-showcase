package io.github.bmarwell.shiro.jwt;


import static org.assertj.core.api.Assertions.assertThat;

import io.github.bmarwell.shiro.jwt.json.JsonbConfigProvider;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.johnzon.jaxrs.jsonb.jaxrs.JsonbJaxrsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractShiroJaxRsIT {

  private static final String JWT = "CnsKICAgICJhbGciOiAiRVMyNTYiCn0"
      + ".CnsKICAgICJyb2xlcyI6IFsKICAgICAgICAiYWRtaW4iCiAgICBdCn0"
      + ".0YkoLImljmhfK0o84Q63r1uJKUB5cafLHSqANO9nH53KL3Dr6K2xYg0KTE8kHzNY_IHvk85dhBWV8p1rPirBoA";

  final Client client = ClientBuilder.newClient()
      .register(new JsonbConfigProvider())
      .register(new JsonbJaxrsProvider<>());

  protected abstract URI getBaseUri();

  @BeforeEach
  public void logOut() {
  }


  @Test
  public void testGetUsersUnauthenticated() {
    final WebTarget usersTarget = client.target(getBaseUri()).path("troopers");
    final Response usersResponse = usersTarget.request(MediaType.APPLICATION_JSON_TYPE)
        .buildGet()
        .invoke();
    assertThat(usersResponse.getStatus())
        .isEqualTo(Status.UNAUTHORIZED.getStatusCode());
  }

  @Test
  public void testGetUsersBasicAuthenticated() {
    final WebTarget usersTarget = client.target(getBaseUri()).path("troopers");
    final String basicToken = Base64.getEncoder().encodeToString(JWT.getBytes(StandardCharsets.UTF_8));
    final Response usersResponse = usersTarget.request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + basicToken)
        .buildGet()
        .invoke();
    assertThat(usersResponse.getStatus())
        .isEqualTo(Status.UNAUTHORIZED.getStatusCode());
  }

}
