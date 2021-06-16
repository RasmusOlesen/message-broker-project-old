package rasmus.olesen.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@EnableScheduling
public class ProducerApplication implements CommandLineRunner {

    @Autowired
    private RabbitMQConfiguration rabbitMQConfiguration;

    @Autowired
    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Write a message and press [enter] to send it.\nType exit and press [enter] to exit application.");

        var scanner = new Scanner(System.in);
        var userInput = "";
        while (true) {
            userInput = scanner.nextLine();
            if ("exit".equals(userInput)) {
                break;
            }
            try {
                sendMessage(userInput);
            } catch (Exception e) {
                System.out.println("Failed to send message: " + userInput + " (" + e.getMessage() + ")");
            }
        }

        System.out.println("Exiting application");

        context.close();
    }

    //TODO Add scheduled sendMessage

    public void sendMessage(final String message) throws IOException, TimeoutException {
        if (message == null) return; // TODO Error handling

        /*
        https://www.rabbitmq.com/channels.html
        Much like connections, channels are meant to be long lived. That is, there is no need to open a channel per operation and doing so would be very inefficient, since opening a channel is a network roundtrip.
        */
        var factory = new ConnectionFactory(); // TODO Re-use factory, connection and channel. Handle recovery.
        factory.setHost("localhost");
        try (var connection = factory.newConnection();
             var channel = connection.createChannel()) {
            //channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueDeclare(rabbitMQConfiguration.getQueueName(), false, false, false, null);
            var basicProperties = new AMQP.BasicProperties().builder().timestamp(new Date()).build();
            channel.basicPublish("", rabbitMQConfiguration.getQueueName(), basicProperties, message.getBytes());
        }
    }
}
