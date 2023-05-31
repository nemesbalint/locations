package locations;

import eventstore.CreateEventCommand;
import eventstore.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class EventStoreGateway {
//
//    private final RestTemplate restTemplate;
//
//
//    public EventStoreGateway(RestTemplateBuilder builder) {
//        this.restTemplate = builder.build();
//    }
//
//    public EventDto createEvent (CreateEventCommand command) {
//        return restTemplate.postForObject("http://localhost:8081/api/events", command, EventDto.class);
//    }

    public EventDto createEvent (CreateEventCommand command) {
        return WebClient.create(getBaseUrl("http://localhost:8081/api/events"))
                .post()
                .uri("/api/events")
                .body(Mono.just(command), CreateEventCommand.class)
                .retrieve()
                .bodyToMono(EventDto.class)
                .block();
    }

    private String getBaseUrl(String eventStoreUrl) {
        return eventStoreUrl.substring(0, eventStoreUrl.indexOf("/api"));
    }
}
