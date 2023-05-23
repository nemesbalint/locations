package locations;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class LocationsService {

//    private ModelMapper modelMapper;

    private LocationMapper locationMapper;

    private AtomicLong id = new AtomicLong();

    @Value("${locations.name-auto-uppercase}")
    private boolean nameAutoUpperCase;

    private List<Location> locations = new ArrayList<>(List.of(
            new Location(id.getAndIncrement(), "Fót", 1.1, 2.2),
            new Location(id.getAndIncrement(), "Dunakeszi", 1.5, 2.5),
            new Location(id.getAndIncrement(), "Vác", 1.91, 2.22))
    );


    //    public LocationsService(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }

    public LocationsService(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    public List<LocationDto> listLocations(Optional<String> prefix, Optional<Double> minLat) {
//        Type targetTypeList = new TypeToken<List<Location>>(){}.getType();

        List<Location> filtered = locations.stream()
                .filter(e -> (prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                        && (minLat.isEmpty() || e.getLat() >= minLat.get())
                )
                .collect(Collectors.toList());

//        return modelMapper.map(filtered, targetTypeList);
        return locationMapper.toDto(filtered);
    }

    public LocationDto findLocationById(long id) {
//        return modelMapper.map(locations.stream()
//                .filter(e -> e.getId() == id)
//                .findAny()
//                .orElseThrow(() -> new IllegalArgumentException("Location not found: "+id)),
//                LocationDto.class);
        return locationMapper.toDto(locations.stream()
                        .filter(e -> e.getId() == id)
                        .findAny()
                        .orElseThrow(() -> new LocationNotFoundException(id)));
    }

    public LocationDto createLocation(CreateLocationCommand command) {
        Location location = new Location(
                id.getAndIncrement(),
                nameAutoUpperCase ? command.getName().toUpperCase() : command.getName(),
                command.getLat(),
                command.getLon());
        locations.add(location);
//        return modelMapper.map(location, LocationDto.class);
        return locationMapper.toDto(location);
    }

    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        Location location = locations.stream()
                .filter(e->e.getId() == id)
                .findFirst()
                .orElseThrow(()->new LocationNotFoundException(id));
        location.setName( nameAutoUpperCase ? command.getName().toUpperCase() : command.getName());
        location.setLat(command.getLat());
        location.setLon(command.getLon());
//        return modelMapper.map(location, LocationDto.class);
        return locationMapper.toDto(location);
    }

    public void deleteLocation(long id) {
        Location location = locations.stream()
                .filter(e->e.getId() == id)
                .findFirst()
                .orElseThrow(()->new LocationNotFoundException(id));
        locations.remove(location);
    }

    public void deleteAllLocations() {
        id = new AtomicLong();
        locations.clear();
    }

}
