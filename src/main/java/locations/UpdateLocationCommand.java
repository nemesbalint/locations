package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Represents a command to update a location.")
@XmlRootElement
public class UpdateLocationCommand {
    @Schema(description = "The new name of the location")
    private String name;
    @Schema(description = "The new latitude of the location", example = "40.7128")
    private double lat;
    @Schema(description = "The new longitude of the location", example = "-74.0060")
    private double lon;
}
