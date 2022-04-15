package io.github.bmarwell.shiro.jwt.it;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.bmarwell.shiro.jwt.dto.Stormtrooper;
import java.util.Arrays;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminIT extends AbstractShiroJaxRsIT {

  @Test
  @Order(1)
  public void testGetTroopersAsAdmin() {
    final WebTarget usersTarget = client.target(getAppUri()).path("troopers");
    final Response usersResponse = usersTarget.request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + getToken(singletonList("admin,root")))
        .buildGet()
        .invoke();
    assertThat(usersResponse.getStatus())
        .isEqualTo(Status.OK.getStatusCode());

    final Stormtrooper[] stormtroopers = usersResponse.readEntity(Stormtrooper[].class);

    Arrays.stream(stormtroopers)
        .forEach(st -> System.out.println(jsonb().toJson(st)));

    assertThat(stormtroopers)
        .hasSize(10)
        .allMatch(st -> st.id().startsWith("FN-"), "id starts with 'FN-'")
    ;
  }
}
