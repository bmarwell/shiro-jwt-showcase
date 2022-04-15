package io.github.bmarwell.shiro.jwt.it;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuestIT extends AbstractShiroJaxRsIT {

  @Test
  public void testGetUsersUnauthenticated() {
    final WebTarget usersTarget = client.target(getAppUri()).path("troopers");
    final Response usersResponse = usersTarget.request(MediaType.APPLICATION_JSON_TYPE)
        .buildGet()
        .invoke();
    assertThat(usersResponse.getStatus())
        .isEqualTo(Status.UNAUTHORIZED.getStatusCode());
  }

  /**
   * Test authenticated user with an invalid token (wrong signature/signer).
   */
  @Test
  public void testGetUsersBasicAuthenticated() {
    final WebTarget usersTarget = client.target(getAppUri()).path("troopers");
    final Response usersResponse = usersTarget.request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + getToken(singletonList("guest")))
        .buildGet()
        .invoke();
    assertThat(usersResponse.getStatus())
        .isEqualTo(Status.FORBIDDEN.getStatusCode());
  }
}
