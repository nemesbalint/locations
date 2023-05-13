package locations;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LocationsServiceTest {

    @Test
    void listLocations() {
        LocationsService locationsService = new LocationsService();
        var locations = locationsService.listLocations();
        assertThat(locations.size()).isEqualTo(3);
    }
}