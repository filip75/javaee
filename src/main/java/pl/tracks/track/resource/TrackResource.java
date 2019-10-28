package pl.tracks.track.resource;

import pl.tracks.race.RaceService;
import pl.tracks.race.model.Race;
import pl.tracks.race.resource.RaceResource;
import pl.tracks.resource.model.EmbeddedResource;
import pl.tracks.resource.model.Link;
import pl.tracks.track.model.Track;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static pl.tracks.resource.UriHelper.pagedUri;
import static pl.tracks.resource.UriHelper.uri;

@Path("tracks")
public class TrackResource {

    @Context
    private UriInfo info;

    @Inject
    private RaceService service;

    private static final int PAGE_SIZE = 2;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracks(@QueryParam("page") @DefaultValue("0") Integer page,
                                 @QueryParam("limit") @DefaultValue("2") Integer limit) {
        List<Track> tracks = service.findAllTracks(page * limit, limit);
        tracks.forEach(track -> track.getLinks().put("self",
                Link.builder().href(uri(info, TrackResource.class, "getTrack", track.getId())).build()));

        int size = service.countTracks();
        int pageCount = size / limit;

        EmbeddedResource.EmbeddedResourceBuilder<List<Track>> builder = EmbeddedResource.<List<Track>>builder()
                .embedded("tracks", tracks);

        builder.link("self",
                Link.builder().href(pagedUri(info, TrackResource.class, "getAllTracks", page)).build());
        builder.link("first",
                Link.builder().href(pagedUri(info, TrackResource.class, "getAllTracks", 0)).build());
        builder.link("last",
                Link.builder().href(pagedUri(info, TrackResource.class, "getAllTracks", Math.max(pageCount - 1, 0))).build());

        if (page < pageCount - 1) {
            builder.link("next",
                    Link.builder().href(pagedUri(info, TrackResource.class, "getAllTracks", page + 1)).build());
        }
        if (page > 0) {
            builder.link("previous",
                    Link.builder().href(pagedUri(info, TrackResource.class, "getAllTracks", page - 1)).build());
        }

        builder.link("addTrack",
                Link.builder().method("POST").href(uri(info, TrackResource.class, "saveTrack")).build());

        return Response.ok(builder.build()).build();
    }

    @GET
    @Path("{id}/races")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrackRaces(@PathParam("id") int id) {
        Track track = service.findTrack(id);
        List<Race> races = service.getTrackRaces(track);
        races.forEach(race -> race.getLinks().put("self",
                Link.builder().href(uri(info, RaceResource.class, "getRace", race.getId())).build()));
        if (track != null) {
            EmbeddedResource<List<Race>> embedded = EmbeddedResource.<List<Race>>builder()
                    .embedded("races", races)
                    .link("self", Link.builder().href(uri(info, TrackResource.class, "getTrackRaces", track.getId())).build())
                    .link("track", Link.builder().href(uri(info, TrackResource.class, "getTrack", track.getId())).build())
                    .build();
            return Response.ok(embedded).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrack(@PathParam("id") int id) {
        Track track = service.findTrack(id);
        if (track == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            track.getLinks().put("addTrack",
                    Link.builder().method("POST").href(uri(info, TrackResource.class, "saveTrack")).build());
            track.getLinks().put("updateTrack",
                    Link.builder().method("POST").href(uri(info, TrackResource.class, "updateTrack", track.getId())).build());
            track.getLinks().put("deleteTrack",
                    Link.builder().method("DELETE").href(uri(info, TrackResource.class, "updateTrack", track.getId())).build());
            track.getLinks().put("self",
                    Link.builder().href(uri(info, TrackResource.class, "getTrack", track.getId())).build());
            track.getLinks().put("races",
                    Link.builder().href(uri(info, TrackResource.class, "getTrackRaces", track.getId())).build());
            track.getLinks().put("tracks",
                    Link.builder().href(uri(info, TrackResource.class, "getAllTracks")).build());
            return Response.ok(track).build();
        }
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveTrack(Track track) {
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
