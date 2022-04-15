package io.github.bmarwell.shiro.jwt.it;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.bmarwell.shiro.jwt.dto.Stormtrooper;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminIT extends AbstractShiroJaxRsIT {

  private static String adminToken;
  private final List<Stormtrooper> troopers = new ArrayList<>();

  @BeforeAll
  public static void getAdminToken() {
    AdminIT.adminToken = getToken(List.of("admin", "root"));
  }

  @Test
  @Order(1)
  public void testGetTroopersAsAdmin() {
    final WebTarget usersTarget = getTroopersTarget(false);
    final Response usersResponse = usersTarget.request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + adminToken)
        .buildGet()
        .invoke();
    assertThat(usersResponse.getStatus())
        .isEqualTo(Status.OK.getStatusCode());

    final Stormtrooper[] stormtroopers = usersResponse.readEntity(Stormtrooper[].class);

    troopers.addAll(asList(stormtroopers));

    assertThat(stormtroopers)
        .hasSize(INITIAL_COUNT)
        .allMatch(st -> st.id().startsWith("FN-"), "id starts with 'FN-'")
    ;
  }

  @Test
  @Order(2)
  public void testDeleteTrooper() {
    // given
    final Stormtrooper stormtrooper = troopers.get(0);

    // when
    final Response deletion = getTroopersTarget(true)
        .resolveTemplate("id", stormtrooper.id())
        .request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + adminToken)
        .buildDelete()
        .invoke();

    // then
    assertThat(deletion.getStatus())
        .isEqualTo(Status.ACCEPTED.getStatusCode());
    troopers.remove(stormtrooper);
  }

  @Test
  @Order(3)
  public void changeTrooper() {
    // given
    final Stormtrooper old = troopers.get(0);
    final Stormtrooper updated = new Stormtrooper(old.id(), "Romulus", "Romulan", "Scammer");

    // when
    final Response update = getTroopersTarget(true)
        .resolveTemplate("id", old.id())
        .request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + adminToken)
        .buildPut(Entity.entity(updated, MediaType.APPLICATION_JSON_TYPE))
        .invoke();

    // then
    assertThat(update.getStatus())
        .isEqualTo(Status.ACCEPTED.getStatusCode());
    troopers.remove(old);
    troopers.add(updated);
  }

  @Test
  @Order(4)
  public void addTroopers() {
    // given
    final Stormtrooper trooper1 = new Stormtrooper("Qo’noS", "Klingon", "Warrior");
    final Stormtrooper trooper2 = new Stormtrooper("Vulcan", "Vulcan", "Ambassador");

    // when
    final Response create1 = getTroopersTarget(false)
        .request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + adminToken)
        .buildPost(Entity.entity(trooper1, MediaType.APPLICATION_JSON_TYPE))
        .invoke();
    final Response create2 = getTroopersTarget(false)
        .request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + adminToken)
        .buildPost(Entity.entity(trooper2, MediaType.APPLICATION_JSON_TYPE))
        .invoke();

    // then
    assertThat(create1.getStatus())
        .isEqualTo(Status.ACCEPTED.getStatusCode());
    troopers.add(create1.readEntity(Stormtrooper.class));

    assertThat(create2.getStatus())
        .isEqualTo(Status.ACCEPTED.getStatusCode());
    troopers.add(create2.readEntity(Stormtrooper.class));
  }

  @Test
  @Order(5)
  public void testGetTroopersAsAdminAtEnd() {
    final WebTarget usersTarget = getTroopersTarget(false);
    final Response usersResponse = usersTarget.request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + adminToken)
        .buildGet()
        .invoke();
    assertThat(usersResponse.getStatus())
        .isEqualTo(Status.OK.getStatusCode());

    final Stormtrooper[] stormtroopers = usersResponse.readEntity(Stormtrooper[].class);

    assertThat(stormtroopers)
        .hasSize(INITIAL_COUNT + 1)
        .allMatch(st -> st.id().startsWith("FN-"), "id starts with 'FN-'")
        .anyMatch(st -> st.planetOfOrigin().equals("Vulcan"))
        .anyMatch(st -> st.planetOfOrigin().equals("Qo’noS"))
    ;
  }

}
