package pl.tracks.driver.resource;

import pl.tracks.driver.model.Driver;
import pl.tracks.race.RaceService;
import pl.tracks.race.model.Race;
import pl.tracks.race.resource.RaceResource;
import pl.tracks.resource.model.EmbeddedResource;
import pl.tracks.resource.model.Link;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static pl.tracks.resource.UriHelper.pagedUri;
import static pl.tracks.resource.UriHelper.uri;

@Path("drivers")
public class DriverResource {
    @Context
    private UriInfo info;

    @Inject
    private RaceService service;

    private static final int PAGE_SIZE = 2;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDrivers(@QueryParam("page") @DefaultValue("0") Integer page) {
        List<Driver> drivers = service.findAllDrivers(page * PAGE_SIZE, PAGE_SIZE);
        drivers.forEach(driver -> driver.getLinks().put("self",
                Link.builder().href(uri(info, DriverResource.class, "getDriver", driver.getId())).build()));

        int size = service.countDrivers();
        int pageCount = size / PAGE_SIZE;

        EmbeddedResource.EmbeddedResourceBuilder<List<Driver>> builder = EmbeddedResource.<List<Driver>>builder()
                .embedded("drivers", drivers);
        builder.link("self",
                Link.builder().href(pagedUri(info, DriverResource.class, "getAllDrivers", page)).build());
        builder.link("first",
                Link.builder().href(pagedUri(info, DriverResource.class, "getAllDrivers", 0)).build());
        builder.link("last",
                Link.builder().href(pagedUri(info, DriverResource.class, "getAllDrivers", Math.max(pageCount - 1, 0))).build());

        if (page < pageCount - 1) {
            builder.link("next",
                    Link.builder().href(pagedUri(info, DriverResource.class, "getAllDrivers", page + 1)).build());
        }
        if (page > 0) {
            builder.link("previous",
                    Link.builder().href(pagedUri(info, DriverResource.class, "getAllDrivers", page - 1)).build());
        }

        builder.link("addDriver",
                Link.builder().method("POST").href(uri(info, DriverResource.class, "saveDriver")).build());

        return Response.ok(builder.build()).build();
    }

    @GET
    @Path("{id}/races")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriverRaces(@PathParam("id") int id) {
        Driver driver = service.findDriver(id);
        List<Race> races = service.getDriverRaces(driver);
        races.forEach(race -> race.getLinks().put("self",
                Link.builder().href(uri(info, RaceResource.class, "getRace", race.getId())).build()));
        if (driver != null) {
            EmbeddedResource<List<Race>> embedded = EmbeddedResource.<List<Race>>builder()
                    .embedded("races", races)
                    .link("self", Link.builder().href(uri(info, DriverResource.class, "getDriverRaces", driver.getId())).build())
                    .link("driver", Link.builder().href(uri(info, DriverResource.class, "getDriver", driver.getId())).build())
                    .build();
            return Response.ok(embedded).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriver(@PathParam("id") int id) {
        Driver driver = service.findDriver(id);
        if (driver == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            driver.getLinks().put("addDriver",
                    Link.builder().method("POST").href(uri(info, DriverResource.class, "saveDriver")).build());
            driver.getLinks().put("updateDriver",
                    Link.builder().method("POST").href(uri(info, DriverResource.class, "updateDriver", driver.getId())).build());
            driver.getLinks().put("deleteDriver",
                    Link.builder().method("DELETE").href(uri(info, DriverResource.class, "updateDriver", driver.getId())).build());
            driver.getLinks().put("self",
                    Link.builder().href(uri(info, DriverResource.class, "getDriver", driver.getId())).build());
            driver.getLinks().put("races",
                    Link.builder().href(uri(info, DriverResource.class, "getDriverRaces", driver.getId())).build());
            driver.getLinks().put("drivers",
                    Link.builder().href(uri(info, DriverResource.class, "getAllDrivers")).build());
            return Response.ok(driver).build();
        }
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveDriver(Driver driver) {
        driver.setId(0);
        service.saveDriver(driver);
        return Response.created(uri(DriverResource.class, "getDriver", driver.getId())).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDriver(@PathParam("id") int id, Driver driver) {
        Driver original = service.findDriver(id);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != driver.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.saveDriver(driver);
            return Response.ok().build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteDriver(@PathParam("id") int id) {
        Driver original = service.findDriver(id);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removeDriver(original);
            return Response.noContent().build();
        }
    }
}
