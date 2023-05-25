package locations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class LocationsService {

    private LocationsRepository repository;
    private LocationMapper locationMapper;
    private LocationsProperties locationsProperties;

    public List<LocationDto> listLocations(Optional<String> prefix, Optional<Double> minLat) {
        List<Location> filtered = repository.findAll().stream()
                .filter(e -> (prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                        && (minLat.isEmpty() || e.getLat() >= minLat.get())
                )
                .collect(Collectors.toList());
        log.debug("listLocations called with prefix {}, result count is {}",prefix, filtered.size());
        return locationMapper.toDto(filtered);
    }

    public LocationDto findLocationById(long id) {
        log.debug("findLocationById called with id: {} ", id);
        return locationMapper.toDto(repository.findById(id).orElseThrow(()-> new IllegalArgumentException("location not found") ));
    }

    public LocationDto createLocation(CreateLocationCommand command) {
        log.debug("createLocation called with command: {}", command);
        Location location = new Location(
                locationsProperties.isNameAutoUpperCase()? command.getName().toUpperCase() : command.getName(),
                command.getLat(),
                command.getLon());
        log.debug("createLocation called with location: {}", location);
        repository.save(location);
        return locationMapper.toDto(location);
    }

    @Transactional
    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        log.debug("updateLocation called with command: {}", command);
        Location location = repository.findById(id).orElseThrow(()->new IllegalArgumentException("location not found"));
        location.setName( locationsProperties.isNameAutoUpperCase() ? command.getName().toUpperCase() : command.getName());
        location.setLat(command.getLat());
        location.setLon(command.getLon());
        return locationMapper.toDto(location);
    }

    public void deleteLocation(long id) {
        log.debug("deleteLocation called with id: {}", id);
        repository.deleteById(id);
    }

    public void deleteAllLocations() {
        log.debug("deleteAllLocations called");
        repository.deleteAll();
    }

}
