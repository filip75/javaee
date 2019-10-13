package pl.tracks.track.model;

import lombok.*;
import pl.tracks.race.model.Race;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Track implements Serializable {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private List<Race> races;

    public Track(Track track) {
        this.id = track.id;
        this.name = track.name;
        this.latitude = track.latitude;
        this.longitude = track.longitude;
        this.races = track.races.stream().map(Race::new).collect(Collectors.toList());
    }
}
