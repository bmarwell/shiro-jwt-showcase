package io.github.bmarwell.shiro.jwt.it;

import static java.util.Collections.singletonList;

import io.github.bmarwell.shiro.jwt.json.JsonbConfigProvider;
import java.net.URI;
import java.util.List;
import java.util.Map;
import javax.json.bind.Jsonb;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.johnzon.jaxrs.jsonb.jaxrs.JsonbJaxrsProvider;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractShiroJaxRsIT {

  private static final JsonbConfigProvider JSONB_CONFIG_PROVIDER = new JsonbConfigProvider();

  static final Client client = ClientBuilder.newClient()
      .register(JSONB_CONFIG_PROVIDER)
      .register(new JsonbJaxrsProvider<>());

  static protected URI getLoginUri() {
    return URI.create("http://localhost:" + System.getProperty("http.port") + "/issuer/login");
  }

  static protected URI getAppUri() {
    return URI.create("http://localhost:" + System.getProperty("http.port") + "/finish");
  }

  @BeforeAll
  public static void cleanUp() {
    final WebTarget usersTarget = client.target(getAppUri()).path("troopers");
    final String token = getToken(singletonList("admin,root"));

    final Response usersResponse = usersTarget
        .request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + token)
        .delete();

    if (usersResponse.getStatus() != Status.ACCEPTED.getStatusCode()) {
      throw new IllegalStateException("Unable to set up");
    }

    final StormTrooperFactory stormTrooperFactory = new StormTrooperFactory();
    for (int ii = 0; ii < 10; ii++) {
      final Response createResponse = usersTarget
          .request(MediaType.APPLICATION_JSON_TYPE)
          .header("Authorization", "Bearer " + token)
          .post(Entity.entity(stormTrooperFactory.createRandomTrooper(), MediaType.APPLICATION_JSON_TYPE));

      if (createResponse.getStatus() != Status.ACCEPTED.getStatusCode()) {
        final String entity = createResponse.readEntity(String.class);
        throw new IllegalStateException(
            "Unable to set up: SC = " + createResponse.getStatus() + ".\nResponse from Server:" + entity + "\n");
      }
    }
  }

  public static String getToken(List<String> roles) {
    final Map<String, String> credentials = Map.of(
        "username", "shiro",
        "password", "shiro"
    );
    final Invocation.Builder loginInvocation = client.target(getLoginUri())
        .queryParam("roles", String.join(",", roles))
        .request(MediaType.APPLICATION_JSON_TYPE);
    final Response loginResponse = loginInvocation
        .post(Entity.entity(credentials, MediaType.APPLICATION_JSON_TYPE));
    final int status = loginResponse.getStatus();
    if (status != Status.ACCEPTED.getStatusCode()) {
      final String entity = loginResponse.readEntity(String.class);
      throw new IllegalStateException(
          "login: expected status 202 but got " + status + ".\n"
              + "Entity:\n" + entity + "\n");
    }

    final Map entity = loginResponse.readEntity(Map.class);

    return (String) entity.get("token");
  }

  public Jsonb jsonb() {
    return JSONB_CONFIG_PROVIDER.getContext(null);
  }

}
