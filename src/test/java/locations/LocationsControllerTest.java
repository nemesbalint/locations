package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationsControllerTest {
    @Mock
    LocationsService locationsService;

    @InjectMocks
    LocationsController locationsController;

    @Test
    void listLocations() {
        when(locationsService.listLocations()).thenReturn ( new ArrayList<>(List.of(
                new Location("Fót", 1.1, 2.2),
                new Location("Dunakeszi", 1.5, 2.5),
                new Location("Göd", 1.9, 2.0))
        ));
        var message = locationsController.listLocations();
        assertThat(message).isEqualTo("[Location{id=null, name='Fót', lat=1.1, lon=2.2}, Location{id=null, name='Dunakeszi', lat=1.5, lon=2.5}, Location{id=null, name='Göd', lat=1.9, lon=2.0}]");
    }
}