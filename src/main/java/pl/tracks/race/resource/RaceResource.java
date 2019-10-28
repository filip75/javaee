package pl.tracks.race.resource;

import pl.tracks.driver.model.Driver;
import pl.tracks.driver.resource.DriverResource;
import pl.tracks.race.RaceService;
import pl.tracks.race.model.Race;
import pl.tracks.resource.model.EmbeddedResource;
import pl.tracks.resource.model.Link;
import pl.tracks.track.resource.TrackResource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

import static pl.tracks.resource.UriHelper.pagedUri;
import static pl.tracks.resource.UriHelper.uri;


@Path("races")
public class RaceResource {

    @Context
    private UriInfo info;

    @Inject
    private RaceService service;

    private static final int PAGE_SIZE = 2;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRaces(@QueryParam("page") @DefaultValue("0") Integer page) {
        List<Race> races = service.findAllRaces(page * PAGE_SIZE, PAGE_SIZE);
        races.forEach(race -> race.getLinks().put("self",
                Link.builder().href(uri(info, RaceResource.class, "getRace", race.getId())).build()));

        int size = service.countRaces();
        int pageCount = size / PAGE_SIZE;

        EmbeddedResource.EmbeddedResourceBuilder<List<Race>> builder = EmbeddedResource.<List<Race>>builder()
                .embedded("races", races);

        builder.link("self",
                Link.builder().href(pagedUri(info, RaceResource.class, "getAllRaces", page)).build());
        builder.link("first",
                Link.builder().href(pagedUri(info, RaceResource.class, "getAllRaces", 0)).build());
        builder.link("last",
                Link.builder().href(pagedUri(info, RaceResource.class, "getAllRaces", Math.max(pageCount - 1, 0))).build());

        if (page < pageCount - 1) {
            builder.link("next",
                    Link.builder().href(pagedUri(info, RaceResource.class, "getAllRaces", page + 1)).build());
        }
        if (page > 0) {
            builder.link("previous",
                    Link.builder().href(pagedUri(info, RaceResource.class, "getAllRaces", page - 1)).build());
        }

        builder.link("addRace",
                Link.builder().method("POST").href(uri(info, RaceResource.class, "saveRace")).build());

        return Response.ok(builder.build()).build();
    }

    @GET
    @Path("{id}/drivers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRaceDrivers(@PathParam("id") int id) {
        Race race = service.findRace(id);
        if (race != null) {
            List<Driver> drivers = race.getDrivers().stream().map(Driver::new).collect(Collectors.toList());
            drivers.forEach(driver -> driver.getLinks().put("self",
                    Link.builder().href(uri(info, DriverResource.class, "getDriver", driver.getId())).build()));

            EmbeddedResource<List<Driver>> embedded = EmbeddedResource.<List<Driver>>builder()
                    .embedded("drivers", drivers)
                    .link("self", Link.builder().href(uri(info, RaceResource.class, "getRaceDrivers", race.getId())).build())
                    .link("race", Link.builder().href(uri(info, RaceResource.class, "getRace", race.getId())).build())
                    .build();
            return Response.ok(embedded).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRace(@PathParam("id") int id) {
        Race race = service.findRace(id);
        if (race == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            race.getLinks().put("addRace",
                    Link.builder().method("POST").href(uri(info, RaceResource.class, "saveRace")).build());
            race.getLinks().put("updateRace",
                    Link.builder().method("POST").href(uri(info, RaceResource.class, "updateRace",race.getId())).build());
            race.getLinks().put("deleteRace",
                    Link.builder().method("DELETE").href(uri(info, RaceResource.class, "updateRace",race.getId())).build());
            race.getLinks().put("self",
                    Link.builder().href(uri(info, RaceResource.class, "getRace", race.getId())).build());
            race.getLinks().put("track",
                    Link.builder().href(uri(info, TrackResource.class, "getTrack", race.getTrack().getId())).build());
            race.getLinks().put("drivers",
                    Link.builder().href(uri(info, RaceResource.class, "getRaceDrivers", race.getId())).build());
            race.getLinks().put("races",
                    Link.builder().href(uri(info, RaceResource.class, "getAllRaces")).build());
            return Response.ok(race).build();
        }

    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveRace(Race race) {
        race.setId(0);
        service.saveRace(race);
        return Response.created(uri(RaceResource.class, "getRace", race.getId())).build();
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
