package pl.tracks.race.view;

import pl.tracks.race.RaceService;
import pl.tracks.race.model.Race;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class RaceCreate extends RaceEdit {
    @Inject
    public RaceCreate(RaceService service) {
        super(service);
    }

    @PostConstruct
    public void init() {
        setRace(new Race());
    }
}
