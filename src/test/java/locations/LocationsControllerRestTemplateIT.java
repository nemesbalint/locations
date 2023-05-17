package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationsControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    LocationsService locationsService;

    @Test
    public void testListLocations() {

        locationsService.deleteAllLocations();

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

        locationsService.deleteAllLocations();

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
                        "/locations/1",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<LocationDto>() {}
                ).getBody();

        assertEquals("Dunakeszi", locationDto.getName());
    }

    @Test
    public void testCreateLocation() {

        locationsService.deleteAllLocations();

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

        locationsService.deleteAllLocations();

        template.postForObject(
                "/locations",
                new CreateLocationCommand("Fót", 1.1, 2.1),
                LocationDto.class
        );

        template.put(
                "/locations/0",
                new CreateLocationCommand("Dunakeszi", 1.1, 2.1),
                LocationDto.class
        );

        LocationDto locationDto =
                template.exchange(
                        "/locations/0",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<LocationDto>() {}
                ).getBody();

        assertEquals("Dunakeszi", locationDto.getName());
    }

    @Test
    public void testDeleteLocation() {
        locationsService.deleteAllLocations();

        template.postForObject(
                "/locations",
                new CreateLocationCommand("Fót", 1.1, 2.1),
                LocationDto.class
        );

        template.put(
                "/locations/0",
                new CreateLocationCommand("Dunakeszi", 1.1, 2.1),
                LocationDto.class
        );

        template.delete("/location/0");

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
