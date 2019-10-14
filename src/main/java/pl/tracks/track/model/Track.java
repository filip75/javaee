package pl.tracks.track.model;

import lombok.*;

import java.io.Serializable;

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
