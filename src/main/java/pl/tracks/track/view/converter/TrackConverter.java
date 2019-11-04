package pl.tracks.track.view.converter;

import pl.tracks.race.RaceService;
import pl.tracks.track.model.Track;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Track.class, managed = true)
@Dependent
public class TrackConverter implements Converter<Track> {

    private RaceService service;

    @Inject
    public TrackConverter(RaceService service) {
        this.service = service;
    }

    @Override
    public Track getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return service.findTrack(Integer.parseInt(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Track track) {
        if (track == null || track.getId() == null) {
            return "";
        }
        return Integer.toString(track.getId());
    }
}
