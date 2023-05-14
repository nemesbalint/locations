package locations;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LocationsServiceTest {

    @Test
    void listLocations() {
        LocationsService locationsService = new LocationsService(new ModelMapper());
        var locations = locationsService.listLocations(
                Optional.of(""), Optional.empty());
        assertThat(locations.size()).isEqualTo(3);
    }
}