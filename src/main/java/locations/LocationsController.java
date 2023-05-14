package locations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    public List<LocationDto> listLocations(@RequestParam Optional<String> prefix,
                                           @RequestParam Optional<Double> minLat) {
        return locationsService.listLocations(prefix, minLat);
    }
    @GetMapping("/locations/{id}")
    public LocationDto findLocationById(@PathVariable("id") long id) {
        return locationsService.findLocationById(id);
    }

}
