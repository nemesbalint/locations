package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a command to create a location.")
@XmlRootElement
public class CreateLocationCommand {
    @Schema(description = "The name of the location.", required = true)
    private String name;
    @Schema(description = "The latitude of the location.", example = "51.5074")
    private double lat;
    @Schema(description = "The longitude of the location.", example = "-0.1278")
    private double lon;
}
