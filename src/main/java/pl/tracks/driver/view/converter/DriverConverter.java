package pl.tracks.driver.view.converter;

import pl.tracks.driver.model.Driver;
import pl.tracks.race.RaceService;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Driver.class, managed = true)
@Dependent
public class DriverConverter implements Converter<Driver> {
    private RaceService service;

    @Inject
    public DriverConverter(RaceService service) {
        this.service = service;
    }

    @Override
    public Driver getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return service.findDriver(Integer.parseInt(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Driver driver) {
        if (driver == null || driver.getId() == null) {
            return "";
        }
        return Integer.toString(driver.getId());
    }
}
