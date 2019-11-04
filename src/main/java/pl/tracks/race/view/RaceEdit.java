package pl.tracks.race.view;

import lombok.Getter;
import lombok.Setter;
import pl.tracks.driver.model.Driver;
import pl.tracks.race.RaceService;
import pl.tracks.race.model.Race;
import pl.tracks.track.model.Track;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class RaceEdit implements Serializable {
    private RaceService service;

    @Setter
    @Getter
    private Race race;

    @Inject
    public RaceEdit(RaceService service) {
        this.service = service;
    }

    public String saveRace() {
        if (race == null) {
            race = new Race();
        }
        service.saveRace(race);
        return "race_list?faces-redirect=true";
    }

    public List<Track> getAvailableTracks() {
        return service.findAllTracks();
    }

    public List<Driver> getAvailableDrivers() {
        return service.findAllDrivers();
    }
}
