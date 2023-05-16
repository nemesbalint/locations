package locations;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/locations")
    public LocationDto createLocation(@RequestBody CreateLocationCommand command) {
        return locationsService.createLocation(command);
    }

    @PutMapping("/locations/{id}")
    public LocationDto updateLocation(@PathVariable("id") long id, @RequestBody UpdateLocationCommand command) {
        return locationsService.updateLocation(id, command);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/locations/{id}")
    public void deleteLocation(@PathVariable("id") long id) {
        locationsService.deleteLocation(id);
    }

//    @ExceptionHandler({LocationNotFoundException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<ProblemDetail> handleNotFound(LocationNotFoundException e) {
//        ProblemDetail problemDetail =
//                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
//        problemDetail.setType(URI.create("location/not-found"));
//        problemDetail.setTitle("Not found");
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
//                .body(problemDetail);
//    }

}
