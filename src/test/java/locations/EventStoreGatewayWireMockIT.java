package locations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventstore.CreateEventCommand;
import eventstore.EventDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureWireMock(port = 0)
public class EventStoreGatewayWireMockIT {
    @Autowired
    EventStoreGateway eventStoreGateway;

    @Test
    public void testCreateEvent() throws JsonProcessingException {
        var resource = "/api/events";
        var objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(new CreateEventCommand("Teszt"));
        var jsonExpected = objectMapper.writeValueAsString(new EventDto(0L, "REST", null, "Teszt"));

        stubFor(post(urlPathEqualTo(resource))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(jsonExpected)
                )
        );

        var eventDto = eventStoreGateway.createEvent(new CreateEventCommand("Teszt"));

//        verify(postRequestedFor(urlPathEqualTo(resource))
//                .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_VALUE))
//                .withRequestBody(equalToJson(json))
//        );

        assertEquals("Teszt", eventDto.getMessage());

    }

}
