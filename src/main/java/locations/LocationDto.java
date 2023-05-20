package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
@Schema(description = "Data transfer object representing a location.")
public class LocationDto {
    @Schema(description = "The unique identifier of the location.")
    @XmlElement(name="id")
    private Long id;
    @Schema(description = "The name of the location.", example = "Name")
    @XmlElement(name="name")
    private String name;
    @Schema(description = "The latitude of the location.", example = "51.5074")
    @XmlElement(name="lat")
    private double lat;
    @Schema(description = "The longitude of the location.", example = "-0.1278")
    @XmlElement(name="lon")
    private double lon;

    /**
     * Constructs a new LocationDto object.
     *
     * @param name The name of the location.
     * @param lat  The latitude of the location.
     * @param lon  The longitude of the location.
     */
    public LocationDto(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
}
