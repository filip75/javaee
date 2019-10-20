package pl.tracks.track.resource;

import pl.tracks.race.RaceService;
import pl.tracks.track.model.Track;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("tracks")
public class TrackResource {
    @Inject
    private RaceService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Track> getAllTracks() {
        return service.findAllTracks();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrack(@PathParam("id") int id) {
        Track track = service.findTrack(id);
        if (track == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(track).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveRace(Track track) {
        track.setId(0);
        service.saveTrack(track);
        return Response.created(UriBuilder.fromResource(TrackResource.class).path(TrackResource.class, "getTrack")
                .build(track.getId())).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTrack(@PathParam("id") int id, Track track) {
        Track original = service.findTrack(id);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (original.getId() != track.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            service.saveTrack(track);
            return Response.ok().build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteTrack(@PathParam("id") int id) {
        Track original = service.findTrack(id);
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.removeTrack(original);
            return Response.noContent().build();
        }
    }
}
