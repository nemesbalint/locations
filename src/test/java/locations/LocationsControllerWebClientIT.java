package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationsControllerWebClientIT {

    @MockBean
    LocationsService locationsService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testCreateLocation() {
        when(locationsService.createLocation(any()))
                .thenReturn(new LocationDto(1L, "Fót", 1.1, 2.1));

        webTestClient
                .post()
                .uri("/locations")
                .bodyValue(new CreateLocationCommand("Fót", 1.1, 2.1))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(LocationDto.class)
                .value(e -> assertEquals("Fót", e.getName()));
    }

    @Test
    public void testFindLocationById() {
        when(locationsService.findLocationById(1L))
                .thenReturn(new LocationDto(1L, "Fót", 1.1, 2.1));

        webTestClient
                .get()
                .uri("/locations/{id}",1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LocationDto.class)
                .value(e -> assertEquals("Fót", e.getName()));
    }

    @Test
    public void testListLocations() {
        when(locationsService.listLocations(any(),any()))
                .thenReturn(List.of(
                        new LocationDto(1L, "Fót", 1.1, 2.1),
                        new LocationDto(2L, "Dunakeszi", 2.1, 3.1)
                ));

        webTestClient
                .get()
                .uri("/locations")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LocationDto.class)
                .hasSize(2)
                .contains(new LocationDto(1L, "Fót", 1.1, 2.1));
    }

    @Test
    public void testUpdateLocation() {
        when(locationsService.updateLocation(anyLong(), any()))
                .thenReturn(new LocationDto(1L, "Fót", 1.1, 2.1));

        webTestClient
                .put()
                .uri("/locations/{id}", 1)
                .bodyValue(new UpdateLocationCommand("Dunakeszi", 1.1, 2.1))
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(LocationDto.class)
                .value(e -> assertEquals("Fót", e.getName()));
    }

    @Test
    public void testDeleteLocation() {
        webTestClient
                .delete()
                .uri("/locations/{id}", 1)
                .exchange()
                .expectStatus().isNoContent();
    }

}
