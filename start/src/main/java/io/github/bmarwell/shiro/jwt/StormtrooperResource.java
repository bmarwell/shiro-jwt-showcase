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
  public Stormtrooper createTrooper(Stormtrooper trooper) {

    return trooperDao.addStormtrooper(trooper);
  }

  @Path("/{id}")
  @POST
  @RequiresPermissions("troopers:update")
  public Stormtrooper updateTrooper(@PathParam("id") String id, Stormtrooper updatedTrooper) throws NotFoundException {

    return trooperDao.updateStormtrooper(id, updatedTrooper);
  }

  @Path("/{id}")
  @DELETE
  @RequiresPermissions("troopers:delete")
  public Response deleteTrooper(@PathParam("id") String id) {
    return Response.accepted().build();
  }
}