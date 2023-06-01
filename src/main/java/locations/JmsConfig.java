package locations;

import com.fasterxml.jackson.databind.ObjectMapper;
import eventstore.CreateEventCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import java.util.Map;

@Configuration
public class JmsConfig {

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setTypeIdPropertyName("_typeId");
        converter.setTypeIdMappings(Map.of("CreateEventCommand", CreateEventCommand.class));
        return converter;
    }
}
