package locations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

//@Service
@Service
public class LocationsService {

    private ModelMapper modelMapper;

    private AtomicLong id = new AtomicLong();

    private List<Location> locations = new ArrayList<>(List.of(
            new Location(id.getAndIncrement(), "Fót", 1.1, 2.2),
            new Location(id.getAndIncrement(), "Dunakeszi", 1.5, 2.5),
            new Location(id.getAndIncrement(), "Vác", 1.91, 2.22))
    );

    public LocationsService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LocationDto> listLocations(Optional<String> prefix, Optional<Double> minLat) {
        Type targetTypeList = new TypeToken<List<Location>>(){}.getType();

        List<Location> filtered = locations.stream()
                .filter(e -> (prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                        && (minLat.isEmpty() || e.getLat() >= minLat.get())
                )
                .collect(Collectors.toList());

        return modelMapper.map(filtered, targetTypeList);
    }

    public LocationDto findLocationById(long id) {
        return modelMapper.map(locations.stream()
                .filter(e -> e.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Location not found: "+id)),
                LocationDto.class);
    }
}
