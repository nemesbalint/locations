package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a location.")
public class Location {
    @Schema(description = "The unique identifier of the location.")
    private Long id;
    @Schema(description = "The name of the location.", example = "Name")
    private String name;
    @Schema(description = "The latitude of the location.", example = "51.5074")
    private double lat;
    @Schema(description = "The longitude of the location.", example = "-0.1278")
    private double lon;

    /**
     * Constructs a new Location object.
     *
     * @param name The name of the location.
     * @param lat  The latitude of the location.
     * @param lon  The longitude of the location.
     */
    public Location(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
}
