package pl.tracks.driver.view;

import pl.tracks.driver.model.Driver;
import pl.tracks.race.RaceService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class DriverList implements Serializable {
    private RaceService service;

    @Inject
    public DriverList(RaceService service) {
        this.service = service;
    }

    public List<Driver> getDrivers() {
        return service.findAllDrivers();
    }

    public String removeDriver(Driver driver) {
        if (driver.getRaces().size() == 0) {
            service.removeDriver(driver);
        }
        return "driver_list?faces-redirect=true";
    }
}
