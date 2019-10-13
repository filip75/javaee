package pl.tracks.race.view.converter;

import pl.tracks.race.RaceService;
import pl.tracks.race.model.Race;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Race.class, managed = true)
@Dependent
public class RaceConverter implements Converter<Race> {
    private RaceService service;

    @Inject
    public RaceConverter(RaceService service) {
        this.service = service;
    }

    @Override
    public Race getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return service.findRace(Integer.parseInt(s));

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Race race) {
        if (race == null) {
            return "";
        }
        return Integer.toString(race.getId());
    }
}
