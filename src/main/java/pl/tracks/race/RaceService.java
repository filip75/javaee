package pl.tracks.race;

import pl.tracks.driver.model.Driver;
import pl.tracks.race.model.Race;
import pl.tracks.track.model.Track;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class RaceService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void init() {
        Track silverstone = new Track("Silverstone", 52.074, -1.017);
        Track monza = new Track("Monza", 45.580, 9.273);
        Track monaco = new Track("Monaco", 22.23, 11.273);

        entityManager.persist(silverstone);
        entityManager.persist(monza);
        entityManager.persist(monaco);

        Race silverstone2019 = new Race(LocalDate.of(2019, Calendar.JUNE, 21),
                "2019 British Grand Prix",
                silverstone);
        Race silverstone2018 = new Race(LocalDate.of(2018, Calendar.JUNE, 18),
                "2018 British Grand Prix",
                silverstone);
        Race monza2019 = new Race(LocalDate.of(2019, Calendar.SEPTEMBER, 11),
                "2019 Italian Grand Prix",
                monza);

        Driver hamilton = new Driver("Lewis", "Hamilton", LocalDate.of(1980, Calendar.FEBRUARY, 1));
        Driver vettel = new Driver("Sebastian", "Vettel", LocalDate.of(1980, Calendar.FEBRUARY, 2));
        Driver schumacher = new Driver("Michael", "Schumacher", LocalDate.of(1980, Calendar.FEBRUARY, 2));
        Driver senna = new Driver("Ayrton", "Senna", LocalDate.of(1980, Calendar.FEBRUARY, 3));
        Driver bottas = new Driver("Valteri", "Bottas", LocalDate.of(1980, Calendar.FEBRUARY, 4));
        Driver ricciardo = new Driver("Daniel", "Ricciardo", LocalDate.of(1980, Calendar.FEBRUARY, 5));

        entityManager.persist(hamilton);
        entityManager.persist(vettel);
        entityManager.persist(schumacher);
        entityManager.persist(senna);
        entityManager.persist(bottas);
        entityManager.persist(ricciardo);

        silverstone2019.getDrivers().add(hamilton);
        silverstone2019.getDrivers().add(vettel);
        silverstone2019.getDrivers().add(schumacher);
        silverstone2019.getDrivers().add(ricciardo);
        silverstone2019.getDrivers().add(bottas);

        entityManager.persist(silverstone2019);
        entityManager.persist(silverstone2018);
        entityManager.persist(monza2019);


    }

    public List<Track> findAllTracks() {
        return entityManager.createNamedQuery(Track.Queries.FIND_ALL, Track.class).getResultList();
    }

    public Track findTrack(int id) {
        return entityManager.find(Track.class, id);
    }

    @Transactional
    public void saveTrack(Track track) {
        if (track.getId() == null) {
            entityManager.persist(track);
        } else {
            entityManager.merge(track);
        }
    }

    @Transactional
    public void removeTrack(Track track) {
        entityManager.remove(entityManager.merge(track));
    }

//    public List<Race> getTrackRaces(Track track) {
//        return races.stream().filter(race -> race.getTrack().equals(track)).map(Race::new).collect(Collectors.toList());
//    }
//
//    public int countTracks() {
//        return tracks.size();
//    }

    public List<Race> findAllRaces() {
        return entityManager.createNamedQuery(Race.Queries.FIND_ALL, Race.class).getResultList();
    }

    public List<Race> findAllRacesByTrack(Track track) {
        return entityManager.createNamedQuery(Race.Queries.FIND_BY_TRACK, Race.class)
                .setParameter("track", track)
                .getResultList();
    }


    public Race findRace(int id) {
        return entityManager.find(Race.class, id);
    }

    @Transactional
    public synchronized void saveRace(Race race) {
        if (race.getId() == null) {
            entityManager.persist(race);
        } else {
            entityManager.merge(race);
        }
    }

    @Transactional
    public void removeRace(Race race) {
        entityManager.remove(entityManager.merge(race));
    }

//    public int countRaces() {
//        return races.size();
//    }

    public List<Driver> findAllDrivers() {
        return entityManager.createNamedQuery(Driver.Queries.FIND_ALL, Driver.class).getResultList();
    }

    public Driver findDriver(int id) {
        return entityManager.find(Driver.class, id);
    }

    @Transactional
    public void saveDriver(Driver driver) {
        if (driver.getId() == null) {
            entityManager.persist(driver);
        } else {
            entityManager.merge(driver);
        }
    }

    @Transactional
    public void removeDriver(Driver driver) {
        entityManager.remove(entityManager.merge(driver));
    }

//    public List<Race> getDriverRaces(Driver driver) {
//        return races.stream().filter(r -> r.getDrivers().stream().anyMatch(d -> d.equals(driver))).map(Race::new).collect(Collectors.toList());
//    }
//
//    public int countDrivers() {
//        return drivers.size();
//    }
}
