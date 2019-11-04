package pl.tracks.driver.model;

import lombok.*;
import pl.tracks.race.model.Race;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@EqualsAndHashCode(exclude = {"races"})
@ToString
@Entity
@NamedQuery(name = Driver.Queries.FIND_ALL, query = "select d from Driver d")
public class Driver implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Driver.findAll";
    }

    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @Getter
    @Setter
    @NotBlank
    private String firstName;

    @Getter
    @Setter
    @NotBlank
    private String lastName;

    @Getter
    @Setter
    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn
    private List<Race> races = new ArrayList<>();

    public Driver(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
