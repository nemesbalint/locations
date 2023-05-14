package locations;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//@Service
@Service
public class LocationsService {

    private ModelMapper modelMapper;

    private List<Location> locations = new ArrayList<>(List.of(
            new Location("Fót", 1.1, 2.2),
            new Location("Dunakeszi", 1.5, 2.5),
            new Location("Vác", 1.91, 2.22))
    );

    public LocationsService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LocationDto> listLocations() {
        Type targetTypeList = new TypeToken<List<Location>>(){}.getType();
        return modelMapper.map(locations, targetTypeList);
    }
}
