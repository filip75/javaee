package pl.tracks.driver.view;

import pl.tracks.driver.model.Driver;
import pl.tracks.race.RaceService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class DriverCreate extends DriverEdit {
    @Inject
    public DriverCreate(RaceService service) {
        super(service);
    }

    @PostConstruct
    public void init() {
        setDriver(new Driver());
    }
}
