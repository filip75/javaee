package pl.tracks.driver.view;

import lombok.Getter;
import lombok.Setter;
import pl.tracks.driver.model.Driver;
import pl.tracks.race.RaceService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class DriverEdit implements Serializable {
    private RaceService service;

    @Setter
    @Getter
    private Driver driver;

    @Inject
    public DriverEdit(RaceService service) {
        this.service = service;
    }

    public String saveDriver() {
        service.saveDriver(driver);
        return "driver_list?faces-redirect=true";
    }
}
