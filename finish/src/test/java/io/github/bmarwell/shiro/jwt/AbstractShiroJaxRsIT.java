package io.github.bmarwell.shiro.jwt;


import static org.assertj.core.api.Assertions.assertThat;

import io.github.bmarwell.shiro.jwt.json.JsonbConfigProvider;
import java.net.URI;
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

  private static final String JWT = "ewogICAgImFsZyI6ICJFUzI1NiIKfQ"
      + ".ewogICAgImlzcyI6ICJodHRwOi8vbG9jYWxob3N0OjkwODEvIiwKICAgICJzdWIiOiAibWUiLAogICAgImlhdCI6IDE2NDk5MzI0NzQsCiAgICAibmJmIjogMTY0OTkzMjQ3NCwKICAgICJleHAiOiAxNjQ5OTkyNDc0LAogICAgImF1ZCI6ICJzaGlyby1qd3QiLAogICAgInJvbGVzIjogWwogICAgICAgICJhZG1pbiIsCiAgICAgICAgInVzZXIiCiAgICBdCn0"
      + ".Nkj5mm9F4awnV7AbnyQWq9MNJZt32Vn-USMYWE8jdczK78pAkfaTo0kZCyCAZe9uMGjCZYOYh46VyAZgv86qdA";

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

  /**
   * Test authenticated user.
   *
   * <p>TODO: Test will fail later.</p>
   */
  @Test
  public void testGetUsersBasicAuthenticated() {
    final WebTarget usersTarget = client.target(getBaseUri()).path("troopers");
    final Response usersResponse = usersTarget.request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + JWT)
        .buildGet()
        .invoke();
    assertThat(usersResponse.getStatus())
        .isEqualTo(Status.OK.getStatusCode());
  }

}
