package pl.tracks.race.view;

import lombok.Getter;
import lombok.Setter;
import pl.tracks.race.RaceService;
import pl.tracks.race.model.Race;
import pl.tracks.track.model.Track;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
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

    @PostConstruct
    public void init() {
        race = new Race();
    }

    public String saveRace() {
        if (race.getDrivers() == null) {
            race.setDrivers(new ArrayList<>());
        }
        service.saveRace(race);
        return "race_list?faces-redirect=true";
    }

    public List<Track> getAvailableTracks() {
        return service.findAllTracks(0, 2);
    }
}
