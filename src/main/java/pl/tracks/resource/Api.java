package pl.tracks.resource;


import pl.tracks.resource.model.EmbeddedResource;
import pl.tracks.resource.model.Link;
import pl.tracks.track.resource.TrackResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class Api {

    @Context
    private UriInfo info;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApi() {
        EmbeddedResource<Void> embedded = EmbeddedResource.<Void>builder()
                .link("tracks", Link.builder().href(
                        info.getBaseUriBuilder()
                                .path(TrackResource.class)
                                .path(TrackResource.class, "getAllTracks")
                                .build())
                        .build())
                .link("self", Link.builder().href(
                        info.getBaseUriBuilder()
                                .path(Api.class)
                                .path(Api.class, "getApi")
                                .build())
                        .build())
                .build();
        return Response.ok(embedded).build();
    }

}
