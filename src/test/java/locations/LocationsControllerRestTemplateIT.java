package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from locations")
public class LocationsControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Test
    public void testListLocations() {

        template.postForObject(
                "/locations",
                new CreateLocationCommand("Fót", 1.1, 2.1),
                LocationDto.class
        );

        template.postForObject(
                "/locations",
                new CreateLocationCommand("Dunakeszi", 2.1, 2.2),
                LocationDto.class
        );

        List<LocationDto> locationDtos =
                template.exchange(
                        "/locations",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LocationDto>>() {}
                ).getBody();

        assertThat(locationDtos)
                .extracting(LocationDto::getName)
                .containsExactly("Fót", "Dunakeszi");
    }

    @Test
    public void testFindLocationById() {

        template.postForObject(
                "/locations",
                new CreateLocationCommand("Fót", 1.1, 2.1),
                LocationDto.class
        );

        template.postForObject(
                "/locations",
                new CreateLocationCommand("Dunakeszi", 2.1, 2.2),
                LocationDto.class
        );


        LocationDto locationDto =
                template.exchange(
                        "/locations/2",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<LocationDto>() {}
                ).getBody();

        assertEquals("Dunakeszi", locationDto.getName());
    }

    @Test
    public void testCreateLocation() {

        LocationDto locationDto =
                template.postForObject(
                    "/locations",
                    new CreateLocationCommand("Fót", 1.1, 2.1),
                    LocationDto.class
                );

        assertEquals("Fót", locationDto.getName());

    }

    @Test
    public void testUpdateLocation() {

        template.postForObject(
                "/locations",
                new CreateLocationCommand("Fót", 1.1, 2.1),
                LocationDto.class
        );

        template.put(
                "/locations/1",
                new CreateLocationCommand("Dunakeszi", 1.1, 2.1),
                LocationDto.class
        );

        LocationDto locationDto =
                template.exchange(
                        "/locations/1",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<LocationDto>() {}
                ).getBody();

        assertEquals("Dunakeszi", locationDto.getName());
    }

    @Test
    public void testDeleteLocation() {

        template.postForObject(
                "/locations",
                new CreateLocationCommand("Fót", 1.1, 2.1),
                LocationDto.class
        );

        template.put(
                "/locations/1",
                new CreateLocationCommand("Dunakeszi", 1.1, 2.1),
                LocationDto.class
        );

        template.delete("/location/1");

        List<LocationDto> locationDtos =
                template.exchange(
                        "/locations",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LocationDto>>() {}
                ).getBody();

        assertThat(locationDtos)
                .extracting(LocationDto::getName)
                .containsExactly( "Dunakeszi");

    }

}
