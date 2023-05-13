package locations;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service
@Service
public class LocationsService {

    private List<Location> locations = new ArrayList<>(List.of(
            new Location("Fót", 1.1, 2.2),
            new Location("Dunakeszi", 1.5, 2.5),
            new Location("Vác", 1.91, 2.22))
    );

    public List<Location> listLocations() {
        return locations;
    }
}
