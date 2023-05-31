package locations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventstore.CreateEventCommand;
import eventstore.EventDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(value = EventStoreGateway.class)
public class EventStoreGatewayRestTemplateIT {
    @Autowired
    EventStoreGateway eventStoreGateway;

    @Autowired
    MockRestServiceServer serviceServer;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testUpdateLocation() throws JsonProcessingException {

        var json = objectMapper.writeValueAsString(new CreateEventCommand("Teszt"));
        var jsonEvent = objectMapper.writeValueAsString(new EventDto(0L, "REST", LocalDateTime.now(), "Teszt"));

        serviceServer.expect(requestTo(startsWith("http://localhost:8081/api/events")))
                .andExpect(content().json(json))
                .andRespond(withSuccess(jsonEvent, MediaType.APPLICATION_JSON) );

        EventDto eventDto = eventStoreGateway.createEvent(new CreateEventCommand("Teszt"));

        assertEquals("Teszt", eventDto.getMessage());
    }

}
