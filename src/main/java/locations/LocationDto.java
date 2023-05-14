package locations;

import lombok.Data;

@Data
public class LocationDto {
    private Long id;
    private String name;
    private double lat;
    private double lon;

    public LocationDto(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
}
