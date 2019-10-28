package pl.tracks.race;

import pl.tracks.driver.model.Driver;
import pl.tracks.race.model.Race;
import pl.tracks.track.model.Track;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RaceService {

    private final List<Track> tracks = new ArrayList<>();
    private final List<Race> races = new ArrayList<>();
    private final List<Driver> drivers = new ArrayList<>();

    @PostConstruct
    private void init() {
        tracks.add(new Track(1, "Silverstone", 52.074, -1.017));
        tracks.add(new Track(2, "Monza", 45.580, 9.273));
        tracks.add(new Track(3, "Monza", 45.580, 9.273));
        tracks.add(new Track(4, "Monza", 45.580, 9.273));
        tracks.add(new Track(5, "Monza", 45.580, 9.273));
        tracks.add(new Track(6, "Monza", 45.580, 9.273));
        tracks.add(new Track(7, "Monza", 45.580, 9.273));
        tracks.add(new Track(8, "Monza", 45.580, 9.273));

        Race silverstone2019 = new Race(1,
                LocalDate.of(2019, Calendar.JUNE, 21),
                "2019 British Grand Prix",
                this.tracks.get(0),
                new ArrayList<>());
        races.add(silverstone2019);

        Race silverstone2018 = new Race(2,
                LocalDate.of(2018, Calendar.JUNE, 18),
                "2018 British Grand Prix",
                this.tracks.get(0),
                new ArrayList<>());
        races.add(silverstone2018);

        Race monza2019 = new Race(3,
                LocalDate.of(2019, Calendar.SEPTEMBER, 11),
                "2019 Italian Grand Prix",
                this.tracks.get(1),
                new ArrayList<>());
        races.add(monza2019);

        Driver hamilton = new Driver(0, "Lewis", "Hamilton", LocalDate.of(1980, Calendar.FEBRUARY, 1));
        Driver vettel = new Driver(1, "Sebastian", "Vettel", LocalDate.of(1980, Calendar.FEBRUARY, 2));
        Driver schumacher = new Driver(2, "Michael", "Schumacher", LocalDate.of(1980, Calendar.FEBRUARY, 2));
        Driver senna = new Driver(3, "Ayrton", "Senna", LocalDate.of(1980, Calendar.FEBRUARY, 3));
        Driver bottas = new Driver(4, "Valteri", "Bottas", LocalDate.of(1980, Calendar.FEBRUARY, 4));
        Driver ricciardo = new Driver(5, "Daniel", "Ricciardo", LocalDate.of(1980, Calendar.FEBRUARY, 5));
        drivers.add(hamilton);
        drivers.add(vettel);
        drivers.add(schumacher);
        drivers.add(senna);
        drivers.add(bottas);
        drivers.add(ricciardo);
        silverstone2018.getDrivers().addAll(drivers);
        silverstone2019.getDrivers().addAll(drivers);
    }

    public synchronized List<Track> findAllTracks(int offset, int limit) {
        return tracks.stream().skip(offset).limit(limit).map(Track::new).collect(Collectors.toList());
    }

    public synchronized Track findTrack(int id) {
        return tracks.stream().filter(track -> track.getId() == id).findFirst().map(Track::new).orElse(null);
    }

    public synchronized void saveTrack(Track track) {
        if (track.getId() != 0) {
            tracks.removeIf(t -> t.getId() == track.getId());
            tracks.add(track);
            races.stream().filter(r -> r.getTrack().getId() == track.getId()).forEach(r -> r.setTrack(track));
        } else {
            track.setId(tracks.stream().mapToInt(Track::getId).max().orElse(0) + 1);
            tracks.add(track);
        }
    }

    public synchronized boolean removeTrack(Track track) {
        Race race = races.stream().filter(r -> r.getTrack().equals(track)).findFirst().orElse(null);
        if (race == null) {
            return tracks.removeIf(t -> t.equals(track));
        }
        return false;
    }

    public List<Race> getTrackRaces(Track track){
        return races.stream().filter(race -> race.getTrack().equals(track)).map(Race::new).collect(Collectors.toList());
    }

    public int countTracks() {
        return tracks.size();
    }

    public synchronized List<Race> findAllRaces(int offset, int limit) {
        return races.stream().skip(offset).limit(limit).map(Race::new).collect(Collectors.toList());
    }

    public synchronized Race findRace(int id) {
        return races.stream().filter(race -> race.getId() == id).findFirst().map(Race::new).orElse(null);
    }


    public synchronized void saveRace(Race race) {
        if (race.getDrivers() == null) {
            race.setDrivers(new ArrayList<>());
        }
        if (race.getId() != 0) {
            races.removeIf(r -> r.getId() == race.getId());
            races.add(race);
        } else {
            race.setId(races.stream().mapToInt(Race::getId).max().orElse(0) + 1);
            races.add(race);
        }
    }

    public synchronized boolean removeRace(Race race) {
        return races.removeIf(r -> r.equals(race));
    }

    public int countRaces() {
        return races.size();
    }

    public synchronized List<Driver> findAllDrivers(int offset, int limit) {
        return drivers.stream().skip(offset).limit(limit).map(Driver::new).collect(Collectors.toList());
    }

    public synchronized Driver findDriver(int id) {
        return drivers.stream().filter(driver -> driver.getId() == id).findFirst().map(Driver::new).orElse(null);
    }


    public synchronized void saveDriver(Driver driver) {
        if (driver.getId() != 0) {
            drivers.removeIf(d -> d.getId() == driver.getId());
            drivers.add(driver);
        } else {
            driver.setId(drivers.stream().mapToInt(Driver::getId).max().orElse(0) + 1);
            drivers.add(driver);
        }
    }

    public synchronized boolean removeDriver(Driver driver) {
        return drivers.removeIf(d -> d.equals(driver));
    }

    public List<Race> getDriverRaces(Driver driver) {
        return races.stream().filter(r -> r.getDrivers().stream().anyMatch(d -> d.equals(driver))).map(Race::new).collect(Collectors.toList());
    }

    public int countDrivers() {
        return drivers.size();
    }
}
