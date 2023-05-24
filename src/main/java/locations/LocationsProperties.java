package locations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "locations")
@Data
@Component
public class LocationsProperties {
    private boolean nameAutoUpperCase;
}
