package pl.tracks.race.model;

import lombok.*;
import pl.tracks.driver.model.Driver;
import pl.tracks.resource.model.Link;
import pl.tracks.track.model.Track;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Race implements Serializable {
    private int id;
    private LocalDate date;
    private String name;
    private Track track;
    @JsonbTransient
    private List<Driver> drivers = new ArrayList<>();
    @JsonbProperty("_links")
    private Map<String, Link> links = new HashMap<>();

    @JsonbTransient
    public Track getTrack(){
        return track;
    }

    public Race(Race race) {
        this.id = race.id;
        this.date = race.date;
        this.name = race.name;
        this.track = race.track;
        this.drivers = race.drivers.stream().map(Driver::new).collect(Collectors.toList());
    }

    public Race(int id, LocalDate date, String name, Track track, List<Driver> drivers) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.track = track;
        this.drivers = drivers;
    }
}
