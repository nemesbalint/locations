package locations;

import eventstore.CreateEventCommand;
import eventstore.EventDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class EventStoreGateway {

    private final JmsTemplate jmsTemplate;

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

    public void sendJmsMessage(String message) {
        jmsTemplate.convertAndSend("eventsQueue", new CreateEventCommand("Location modified: "+message));
    }

}
