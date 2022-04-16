/*
 * Copyright (C) 2022 Benjamin Marwell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
   * Test authenticated user with an invalid token (wrong signature/signer).
   */
  @Test
  public void testGetUsersBasicAuthenticated() {
    final WebTarget usersTarget = client.target(getBaseUri()).path("troopers");
    final Response usersResponse = usersTarget.request(MediaType.APPLICATION_JSON_TYPE)
        .header("Authorization", "Bearer " + JWT)
        .buildGet()
        .invoke();
    assertThat(usersResponse.getStatus())
        .isEqualTo(Status.UNAUTHORIZED.getStatusCode());
  }

}
