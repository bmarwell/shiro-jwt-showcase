/*
 * Copyright (C) 2022-2023 The shiro-jjwt-showcase team
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
package io.github.bmarwell.shiro.issuer;

import io.github.bmarwell.shiro.issuer.dto.LoginCredentials;
import io.github.bmarwell.shiro.issuer.services.CredentialsValidator;
import io.github.bmarwell.shiro.issuer.services.TokenService;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
  public Response doLogin(LoginCredentials credentials,
      @QueryParam("roles") @DefaultValue("") String roles) {
    validator.validate(credentials);
    String jwt = tokenService.createJwt(credentials, List.of(roles.split(",")));

    return Response.accepted()
        .entity(Map.of("token", jwt))
        .build();
  }

}
