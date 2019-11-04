package pl.tracks.track.model;

import lombok.*;
import pl.tracks.race.model.Race;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@EqualsAndHashCode(exclude = {"races"})
@ToString(exclude = {"races"})
@Entity
@NamedQuery(name = Track.Queries.FIND_ALL, query = "select t from Track t")
public class Track implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Track.findAll";
    }

    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    @NotNull
    private double latitude;

    @Getter
    @Setter
    @NotNull
    private double longitude;

    @Getter
    @Setter
    @OneToMany(mappedBy = "track", fetch = FetchType.EAGER)
    private List<Race> races = new ArrayList<>();

    public Track(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
