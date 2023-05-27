package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LocationsRepositoryIT {
    @Autowired
    LocationsRepository repository;


    @Test
    public void testFindById() {
        var location = new Location("Fót", 1.1, 2.1);
        repository.save(location);

        location = new Location("Dunakeszi", 2.1, 3.1);
        repository.save(location);

        location = repository.findById(1L).orElseThrow(()-> new IllegalArgumentException("location not found"));

        assertThat(location)
                .extracting(Location::getName)
                .toString()
                .startsWith("Fót");
    }


    @Test
    public void testFindAll() {
        var location = new Location("Fót", 1.1, 2.1);
        repository.save(location);

        location = new Location("Dunakeszi", 2.1, 3.1);
        repository.save(location);

        var locations = repository.findAll();

        assertThat(locations)
                .extracting(Location::getName)
                .hasSize(2)
                .containsExactly("Fót", "Dunakeszi")
              ;
    }


    @Test
    public void testSaveLocation() {
        Location location = new Location("Fót", 1.1, 2.1);
        repository.save(location);

        List<Location> locations = repository.findAll();

        assertThat(locations)
                .extracting(Location::getName)
                .containsExactly("Fót");
    }

    @Test
    public void testUpdateLocation() {
        Location location = new Location("Fót", 1.1, 2.1);
        repository.save(location);

        List<Location> locations = repository.findAll();

        assertThat(locations)
                .extracting(Location::getName)
                .containsExactly("Fót");

        location = locations.get(0);

        location.setName("Dunakeszi");
        repository.save(location);

        assertThat(locations)
                .extracting(Location::getName)
                .containsExactly("Dunakeszi");
    }

    @Test
    public void testDeleteById() {
        var location = new Location("Fót", 1.1, 2.1);
        repository.save(location);

        location = new Location("Dunakeszi", 2.1, 3.1);
        repository.save(location);

        repository.deleteById(1L);

        var locations = repository.findAll();

        assertThat(locations)
                .extracting(Location::getName)
                .hasSize(1)
                .containsExactly( "Dunakeszi")
        ;
    }
}
