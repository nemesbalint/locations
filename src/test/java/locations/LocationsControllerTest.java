package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationsControllerTest {
    @Mock
    LocationsService locationsService;

    @InjectMocks
    LocationsController locationsController;

    @Test
    void listLocations() {
        when(locationsService.listLocations(Optional.of("Fót"),
                Optional.empty())).thenReturn ( new ArrayList<>(List.of(
                new LocationDto("Fót", 1.1, 2.2))
        ));
        var message = locationsController.listLocations(
                Optional.of("Fót"), Optional.empty());
        assertThat(message)
                .hasSize(1)
                .extracting(LocationDto::getName)
                .contains("Fót");
    }
}