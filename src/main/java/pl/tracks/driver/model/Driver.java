package pl.tracks.driver.model;

import lombok.*;
import pl.tracks.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Driver implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @JsonbProperty("_links")
    private Map<String, Link> links = new HashMap<>();

    public Driver(int id, String firstName, String lastName, LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Driver(Driver driver) {
        this.id = driver.id;
        this.firstName = driver.firstName;
        this.lastName = driver.lastName;
        this.dateOfBirth = driver.dateOfBirth;
    }
}
