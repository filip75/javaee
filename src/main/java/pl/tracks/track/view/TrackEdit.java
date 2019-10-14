package pl.tracks.track.view;

import lombok.Getter;
import lombok.Setter;
import pl.tracks.race.RaceService;
import pl.tracks.track.model.Track;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class TrackEdit implements Serializable {
    private RaceService service;

    @Setter
    @Getter
    private Track track;

    @Inject
    public TrackEdit(RaceService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        track = new Track();
    }

    public String saveTrack() {
        service.saveTrack(track);
        return "track_list?faces-redirect=true";
    }
}
