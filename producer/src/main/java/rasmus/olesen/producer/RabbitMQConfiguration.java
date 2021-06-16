package rasmus.olesen.producer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//TODO Refactor to shared module
@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitMQConfiguration {

    private String exchangeName;
    private String queueName;
    private String routingKey;

}
