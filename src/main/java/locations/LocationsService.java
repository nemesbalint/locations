package locations;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocationsService {
    private AtomicLong id = new AtomicLong();
    private LocationMapper locationMapper;
    private LocationsProperties locationsProperties;
    private List<Location> locations = new ArrayList<>(List.of(
            new Location(id.getAndIncrement(), "Fót", 1.1, 2.2),
            new Location(id.getAndIncrement(), "Dunakeszi", 1.5, 2.5),
            new Location(id.getAndIncrement(), "Vác", 1.91, 2.22))
    );

    public LocationsService(LocationMapper locationMapper, LocationsProperties locationsProperties) {
        this.locationMapper = locationMapper;
        this.locationsProperties = locationsProperties;
    }

    public List<LocationDto> listLocations(Optional<String> prefix, Optional<Double> minLat) {
        List<Location> filtered = locations.stream()
                .filter(e -> (prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                        && (minLat.isEmpty() || e.getLat() >= minLat.get())
                )
                .collect(Collectors.toList());
        log.debug("listLocations called with prefix {}, result count is {}",prefix, filtered.size());
        return locationMapper.toDto(filtered);
    }

    public LocationDto findLocationById(long id) {
        log.debug("findLocationById called with id: {} ", id);
        return locationMapper.toDto(locations.stream()
                        .filter(e -> e.getId() == id)
                        .findAny()
                        .orElseThrow(() -> new LocationNotFoundException(id)));
    }

    public LocationDto createLocation(CreateLocationCommand command) {
        log.debug("createLocation called with command: {}", command);
        Location location = new Location(
                id.getAndIncrement(),
                locationsProperties.isNameAutoUpperCase()? command.getName().toUpperCase() : command.getName(),
                command.getLat(),
                command.getLon());
        locations.add(location);
        return locationMapper.toDto(location);
    }

    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        log.debug("updateLocation called with command: {}", command);
        Location location = locations.stream()
                .filter(e->e.getId() == id)
                .findFirst()
                .orElseThrow(()->new LocationNotFoundException(id));
        location.setName( locationsProperties.isNameAutoUpperCase() ? command.getName().toUpperCase() : command.getName());
        location.setLat(command.getLat());
        location.setLon(command.getLon());
        return locationMapper.toDto(location);
    }

    public void deleteLocation(long id) {
        log.debug("deleteLocation called with id: {}", id);
        Location location = locations.stream()
                .filter(e->e.getId() == id)
                .findFirst()
                .orElseThrow(()->new LocationNotFoundException(id));
        locations.remove(location);
    }

    public void deleteAllLocations() {
        log.debug("deleteAllLocations called");
        id = new AtomicLong();
        locations.clear();
    }

}
