package pl.tracks.track.view;

import pl.tracks.race.RaceService;
import pl.tracks.track.model.Track;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class TrackList {
    private RaceService service;

    @Inject
    public TrackList(RaceService service) {
        this.service = service;
    }

    public List<Track> getTracks() {
        return service.findAllTracks();
    }

    public String removeTrack(Track track) {
        if (service.removeTrack(track)) {
            //            TODO
        }
        return "track_list?faces-redirect=true";
    }
}
