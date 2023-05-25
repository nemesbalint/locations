package locations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "locations")
public class LocationsProperties {
    private boolean nameAutoUpperCase;
}
