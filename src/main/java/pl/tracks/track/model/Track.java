package pl.tracks.track.model;

import lombok.*;
import pl.tracks.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Track implements Serializable {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    @JsonbProperty("_links")
    private Map<String, Link> links = new HashMap<>();

    public Track(int id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Track() {
        id = 0;
        name = "";
        latitude = 0.0;
        longitude = 0.0;
    }

    public Track(Track track) {
        this.id = track.id;
        this.name = track.name;
        this.latitude = track.latitude;
        this.longitude = track.longitude;
    }
}
