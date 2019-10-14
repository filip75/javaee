package pl.tracks.race.model;

import lombok.*;
import pl.tracks.driver.model.Driver;
import pl.tracks.track.model.Track;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
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
    private List<Driver> drivers;

    public Race(Race race) {
        this.id = race.id;
        this.date = race.date;
        this.name = race.name;
        this.track = race.track;
        this.drivers = race.drivers.stream().map(Driver::new).collect(Collectors.toList());
    }
}
