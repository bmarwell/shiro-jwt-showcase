package io.github.bmarwell.shiro.jwt;


import io.github.bmarwell.shiro.jwt.dto.Stormtrooper;
import io.github.bmarwell.shiro.jwt.service.StormtrooperDao;
import java.util.Collection;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;

@ApplicationScoped
@Default
@Path("/troopers")
@Produces(MediaType.APPLICATION_JSON)
@RequiresUser
public class StormtrooperResource {

  @Inject
  private StormtrooperDao trooperDao;

  public StormtrooperResource() {
  }

  public StormtrooperResource(StormtrooperDao trooperDao) {
    this.trooperDao = trooperDao;
  }


  @GET
  @RequiresPermissions("troopers:read")
  public Collection<Stormtrooper> listTroopers() {
    return trooperDao.listStormtroopers();
  }

  @Path("/")
  @DELETE
  @RequiresPermissions("troopers:delete")
  public Response deleteAllTroopers() {
    trooperDao.deleteAllStormTroopers();
    return Response.accepted().build();
  }

  @Path("/{id}")
  @GET
  @RequiresPermissions("troopers:read")
  public Optional<Stormtrooper> getTrooper(@PathParam("id") String id) throws NotFoundException {

    Optional<Stormtrooper> stormtrooper = trooperDao.getStormtrooper(id);

    if (stormtrooper.isEmpty()) {
      throw new NotFoundException();
    }

    return stormtrooper;
  }

  @POST
  @RequiresPermissions("troopers:create")
  public Response createTrooper(Stormtrooper trooper) {

    return Response.accepted(trooperDao.addStormtrooper(trooper))
        .build();
  }

  @Path("/{id}")
  @PUT
  @RequiresPermissions("troopers:update")
  public Response updateTrooper(@PathParam("id") String id, Stormtrooper updatedTrooper) throws NotFoundException {

    return Response.accepted(trooperDao.updateStormtrooper(id, updatedTrooper))
        .build();
  }

  @Path("/{id}")
  @DELETE
  @RequiresPermissions("troopers:delete")
  public Response deleteTrooper(@PathParam("id") String id) {
    trooperDao.deleteStormtrooper(id);
    return Response.accepted().build();
  }
}
