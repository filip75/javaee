package pl.tracks.track.view;

import pl.tracks.race.RaceService;
import pl.tracks.track.model.Track;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class TrackCreate extends TrackEdit {
    @Inject
    public TrackCreate(RaceService service) {
        super(service);
    }

    @PostConstruct
    public void init() {
        setTrack(new Track());
    }
}
