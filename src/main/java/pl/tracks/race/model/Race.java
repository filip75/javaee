package pl.tracks.race.model;

import lombok.*;
import pl.tracks.driver.model.Driver;
import pl.tracks.track.model.Track;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"drivers"})
@Entity
@NamedQuery(name = Race.Queries.FIND_ALL, query = "select r from Race r")
@NamedQuery(name = Race.Queries.FIND_BY_TRACK, query = "select r from Race r where :track = r.track")
public class Race implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Race.findAll";
        public static final String FIND_BY_TRACK = "Race.findByTrack";
    }

    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @Getter
    @Setter
    @NotNull
    private LocalDate date;

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Track track;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Driver> drivers = new ArrayList<>();


    public Race(LocalDate date, String name, Track track) {
        this.date = date;
        this.name = name;
        this.track = track;
    }
}
