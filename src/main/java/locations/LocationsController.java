package locations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationsController {
//    private List<Location> locations = new ArrayList<>(List.of(
//            new Location("Fót", 1.1, 2.2),
//            new Location("Dunakeszi", 1.5, 2.5),
//            new Location("Göd", 1.9, 2.0))
//    );

    private LocationsService locationsService;

    public LocationsController(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping("/locations")
    public List<LocationDto> listLocations() {
        return locationsService.listLocations();
    }

}
