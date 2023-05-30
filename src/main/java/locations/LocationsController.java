package locations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Schema(description = "Controller for managing locations.")
@Tag(name = "Controller for managing locations.")
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

    @GetMapping(value = "/locations", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get a list of locations", description = "Retrieve a list of locations based on optional parameters.")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDto.class)))
    public List<LocationDto> listLocations( @RequestParam(required = false) @Schema(description = "Optional prefix string to filter locations by name.") Optional<String> prefix,
                                            @RequestParam(required = false) @Schema(description = "Optional minimum latitude value to filter locations by.") Optional<Double> minLat) {
        return locationsService.listLocations(prefix, minLat);
    }

    @GetMapping(value = "/locations2", produces = {MediaType.APPLICATION_JSON_VALUE,  "text/xml;charset=UTF-8"},
            consumes = {MediaType.APPLICATION_JSON_VALUE,  "text/xml;charset=UTF-8"})
    @Operation(summary = "Get a list of locations as json or xml", description = "Retrieve a list of locations as json or xml based on optional parameters.")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDto.class)))
    public LocationsDto listLocationsAsJsonAndXml( @RequestParam(required = false) @Schema(description = "Optional prefix string to filter locations by name.") Optional<String> prefix,
                                            @RequestParam(required = false) @Schema(description = "Optional minimum latitude value to filter locations by.") Optional<Double> minLat) {
        return new LocationsDto(locationsService.listLocations(prefix, minLat));
    }

    @GetMapping(value = "/locations/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,  "text/xml;charset=UTF-8"},
            consumes = {MediaType.APPLICATION_JSON_VALUE,  "text/xml;charset=UTF-8"})
    @Operation(summary = "Get a location by ID", description = "Retrieve a location based on its ID.")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDto.class)))
    @ApiResponse(responseCode = "404", description = "Location not found")
    public LocationDto findLocationById(
            @PathVariable("id") @Schema(description = "The ID of the location.") long id) {
        return locationsService.findLocationById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/locations",
            produces = {MediaType.APPLICATION_JSON_VALUE,  "text/xml;charset=UTF-8"},
            consumes = {MediaType.APPLICATION_JSON_VALUE,  "text/xml;charset=UTF-8"})
    @Operation(summary = "Create a new location", description = "Create a new location based on the provided data.")
    @ApiResponse(responseCode = "201", description = "Location created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDto.class)))
    public LocationDto createLocation(
            @RequestBody @Schema(description = "Command object for creating a location.") CreateLocationCommand command) {
        return locationsService.createLocation(command);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value = "/locations/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,  "text/xml;charset=UTF-8"},
            consumes = {MediaType.APPLICATION_JSON_VALUE,  "text/xml;charset=UTF-8"})
    @Operation(summary = "Update a location", description = "Update an existing location based on the provided data.")
    @ApiResponse(responseCode = "202", description = "Location updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDto.class)))
    @ApiResponse(responseCode = "404", description = "Location not found")
    public LocationDto updateLocation(
            @PathVariable("id") @Schema(description = "The ID of the location to update.") long id,
            @RequestBody @Schema(description = "Command object for updating a location.") UpdateLocationCommand command) {
        return locationsService.updateLocation(id, command);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/locations/{id}")
    @Operation(summary = "Delete a location", description = "Delete a location based on its ID.")
    @ApiResponse(responseCode = "204", description = "Location deleted")
    @ApiResponse(responseCode = "404", description = "Location not found")
    public void deleteLocation(
            @PathVariable("id") @Schema(description = "The ID of the location to delete.") long id) {
        locationsService.deleteLocation(id);
    }

}
