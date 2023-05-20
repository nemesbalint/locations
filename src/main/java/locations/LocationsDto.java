package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "locations")
@XmlAccessorType(XmlAccessType.FIELD)
@Schema(description = "Data transfer object representing a location list for xml responses.")
public class LocationsDto {
    @Schema(description = "The location list")
    @XmlElement(name="location")
    List<LocationDto> locations;
}
