package locations;

import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {
    @Bean
    public InMemoryHttpExchangeRepository httpTraceRepository () {
        return new InMemoryHttpExchangeRepository();
    }

    @Bean
    public AuditEventRepository auditEventRepository () {
        return new InMemoryAuditEventRepository();
    }
}
