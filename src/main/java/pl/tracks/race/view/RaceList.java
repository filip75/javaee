package pl.tracks.race.view;

import lombok.Getter;
import lombok.Setter;
import pl.tracks.race.RaceService;
import pl.tracks.race.model.Race;
import pl.tracks.track.model.Track;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class RaceList {
    private RaceService service;

    @Setter
    @Getter
    private Track track;

    @Inject
    public RaceList(RaceService service) {
        this.service = service;
    }

    public List<Race> getRaces() {
        if (track == null) {
            return service.findAllRaces();
        } else {
            return service.findAllRacesByTrack(track);
        }
    }

    public String removeRace(Race race) {
        service.removeRace(race);
        return "race_list?faces-redirect=true";
    }

    public List<Track> getAvailableTracks() {
        return service.findAllTracks();
    }

}
