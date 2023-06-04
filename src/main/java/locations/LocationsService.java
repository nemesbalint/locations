package locations;

import eventstore.CreateEventCommand;
import eventstore.EventDto;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class LocationsService {

    public static final String LOCATIONS_CREATED_COUNT = "locations.created";
    private LocationsRepository repository;
    private LocationMapper locationMapper;
    private LocationsProperties locationsProperties;
    private EventStoreGateway eventStoreGateway;
    private MeterRegistry meterRegistry;
    private ApplicationEventPublisher eventPublisher;

    @PostConstruct
    public void init() {
        Counter.builder(LOCATIONS_CREATED_COUNT)
                .baseUnit("location")
                .description("Number of locations created")
                .register(meterRegistry);
    }

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
        meterRegistry.counter(LOCATIONS_CREATED_COUNT).increment();
        repository.save(location);
        eventStoreGateway.sendJmsMessage(location.toString());
        eventPublisher.publishEvent(new AuditApplicationEvent("anonymous", "location_has_been_created",
                Map.of("name", command.getName(), "lat", command.getLat(), "lon", command.getLon())));
        return locationMapper.toDto(location);
    }

    @Transactional
    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        log.debug("updateLocation called with command: {}", command);
        Location location = repository.findById(id).orElseThrow(()->new IllegalArgumentException("location not found"));
        location.setName( locationsProperties.isNameAutoUpperCase() ? command.getName().toUpperCase() : command.getName());
        location.setLat(command.getLat());
        location.setLon(command.getLon());
        EventDto eventDto = eventStoreGateway.createEvent(new CreateEventCommand("Locations updated: "+id));
        log.debug("updateLocation createEvent result {}", eventDto);
        eventStoreGateway.sendJmsMessage(location.toString());
        return locationMapper.toDto(location);
    }

    public void deleteLocation(long id) {
        EventDto eventDto = eventStoreGateway.createEvent(new CreateEventCommand("Locations deleted: "+id));
        log.debug("deleteLocation createEvent result {}", eventDto);
        log.debug("deleteLocation called with id: {}", id);
        eventStoreGateway.sendJmsMessage("id: "+id);
        repository.deleteById(id);
    }

    public void deleteAllLocations() {
        log.debug("deleteAllLocations called");
        repository.deleteAll();
    }

}
