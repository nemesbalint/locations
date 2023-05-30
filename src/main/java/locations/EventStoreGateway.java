package locations;

import com.fasterxml.jackson.databind.ObjectMapper;
import eventstore.CreateEventCommand;
import eventstore.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EventStoreGateway {
    private final RestTemplate restTemplate;

    @Autowired
    private LocationsProperties locationsProperties;

    public EventStoreGateway(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public EventDto createEvent (CreateEventCommand command) {
        return restTemplate.postForObject(locationsProperties.getEventStoreUrl(), command, EventDto.class);
    }
}
