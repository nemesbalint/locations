package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LocationsServiceTest {

    @Autowired
    LocationsDao locationsDao;

    @Test
    void listLocations() {
//        LocationsService locationsService = new LocationsService(new ModelMapper());
        LocationsProperties locationsProperties = new LocationsProperties();
        locationsProperties.setNameAutoUpperCase(false);

        LocationsService locationsService = new LocationsService(locationsDao, new LocationMapperImpl(), locationsProperties);
        var locations = locationsService.listLocations(
                Optional.of(""), Optional.empty());
        assertThat(locations.size()).isEqualTo(3);
    }
}