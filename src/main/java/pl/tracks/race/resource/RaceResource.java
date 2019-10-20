package pl.tracks.race.resource;

import pl.tracks.race.RaceService;
import pl.tracks.race.model.Race;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("races")
public class RaceResource {
    @Inject
    private RaceService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Race> getAllRaces() {
        return service.findAllRaces();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRace(@PathParam("id") int id) {
        Race race = service.findRace(id);
        if (race == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(race).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveRace(Race race) {
        race.setId(0);
        service.saveRace(race);
        return Response.created(UriBuilder.fromResource(RaceResource.class).path(RaceResource.class, "getRace")
                .build(race.getId())).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRace(@PathParam("id") int id, Race race) {
        Race original = service.findRace(id);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != race.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.saveRace(race);
            return Response.ok().build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteRace(@PathParam("id") int id) {
        Race original = service.findRace(id);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removeRace(original);
            return Response.noContent().build();
        }
    }
}
