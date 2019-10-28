package pl.tracks.race.view;

import pl.tracks.race.RaceService;
import pl.tracks.race.model.Race;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class RaceList {
    private RaceService service;

    @Inject
    public RaceList(RaceService service) {
        this.service = service;
    }

    public List<Race> getRaces() {
        return service.findAllRaces(0, 2);
    }

    public String removeRace(Race race) {
        if (service.removeRace(race)) {
//            TODO
        }
        return "race_list?faces-redirect=true";
    }
}
