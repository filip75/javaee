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
        tracks.add(new Track(1, "Silverstone", 52.074, -1.017, new ArrayList<>()));
        tracks.add(new Track(2, "Monza", 45.580, 9.273, new ArrayList<>()));

        Race silverstone2019 = new Race(0,
                LocalDate.of(2019, Calendar.JUNE, 21),
                "2019 British Grand Prix",
                this.tracks.get(0),
                new ArrayList<>());
        races.add(silverstone2019);
        this.tracks.get(0).getRaces().add(silverstone2019);

        Race silverstone2018 = new Race(0,
                LocalDate.of(2018, Calendar.JUNE, 18),
                "2018 British Grand Prix",
                this.tracks.get(0),
                new ArrayList<>());
        races.add(silverstone2018);
        this.tracks.get(0).getRaces().add(silverstone2018);

        Race monza2019 = new Race(0,
                LocalDate.of(2019, Calendar.SEPTEMBER, 11),
                "2019 Italian Grand Prix",
                this.tracks.get(0),
                new ArrayList<>());
        races.add(monza2019);
        this.tracks.get(1).getRaces().add(monza2019);
    }

    public synchronized List<Track> findAllTracks() {
        return tracks.stream().map(Track::new).collect(Collectors.toList());
    }

    public synchronized Track findTrack(int id) {
        return tracks.stream().filter(track -> track.getId() == id).findFirst().map(Track::new).orElse(null);
    }

    public synchronized void saveTrack(Track track) {
        if (track.getId() != 0) {
            tracks.removeIf(track1 -> track.getId() == track.getId());
            tracks.add(track);
        } else {
            track.setId(tracks.stream().mapToInt(Track::getId).max().orElse(0) + 1);
            tracks.add(track);
        }
    }

    public synchronized void removeTrack(Track track) {
        tracks.removeIf(t -> t.equals(track));
    }

    public synchronized List<Race> findAllRaces() {
        return races.stream().map(Race::new).collect(Collectors.toList());
    }

    public synchronized Race findRace(int id) {
        return races.stream().filter(race -> race.getId() == id).findFirst().map(Race::new).orElse(null);
    }


    public synchronized void saveRace(Race race) {
        if (race.getId() != 0) {
            races.removeIf(track1 -> race.getId() == race.getId());
            races.add(race);
        } else {
            race.setId(races.stream().mapToInt(Race::getId).max().orElse(0) + 1);
            races.add(race);
        }
        tracks.stream().filter(t -> t.equals(race.getTrack())).findFirst().
                ifPresent(track -> track.getRaces().add(race));
    }

    public synchronized void removeRace(Race race) {
        races.removeIf(r -> r.equals(race));
        tracks.stream().filter(t -> t.getRaces().stream().anyMatch(r -> r.equals(race))).findFirst().
                ifPresent(track -> track.getRaces().remove(race));
    }
}
