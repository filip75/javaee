package pl.tracks.view.converter;

import pl.tracks.race.RaceService;
import pl.tracks.track.model.Track;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

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
        if (track == null) {
            return "";
        }
        return Integer.toString(track.getId());
    }
}
