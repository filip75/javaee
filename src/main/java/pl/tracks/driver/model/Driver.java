package pl.tracks.driver.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

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


    public Driver(Driver driver) {
        this.id = driver.id;
        this.firstName = driver.firstName;
        this.lastName = driver.lastName;
        this.dateOfBirth = driver.dateOfBirth;
    }
}
