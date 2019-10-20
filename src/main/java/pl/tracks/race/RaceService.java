package pl.tracks.race;

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

    @PostConstruct
    private void init() {
        tracks.add(new Track(1, "Silverstone", 52.074, -1.017));
        tracks.add(new Track(2, "Monza", 45.580, 9.273));

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
    }

    public synchronized List<Track> findAllTracks() {
        return tracks.stream().map(Track::new).collect(Collectors.toList());
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

    public synchronized List<Race> findAllRaces() {
        return races.stream().map(Race::new).collect(Collectors.toList());
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
}
