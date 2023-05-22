package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    @Schema(description = "The latitude of the location.", example = "51.5074")
    @Min(value = -90) @Max(value = 90)
    private double lat;
    @Schema(description = "The longitude of the location.", example = "-0.1278")
    @Min(value = -180) @Max(value = 180)
    private double lon;
}
