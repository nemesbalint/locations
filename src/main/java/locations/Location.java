package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a location.")
@Entity
@Table(name = "locations")
public class Location {
    @Schema(description = "The unique identifier of the location.")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "The name of the location.", example = "Name")
    @Column
    private String name;
    @Schema(description = "The latitude of the location.", example = "51.5074")
    @Column
    private double lat;
    @Schema(description = "The longitude of the location.", example = "-0.1278")
    @Column
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
