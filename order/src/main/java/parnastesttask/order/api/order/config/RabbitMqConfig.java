package parnastesttask.order.api.order.config;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  @Value("${app.rabbitmq.exchange}")
  private String exchange;

  @Value("${app.rabbitmq.queue}")
  private String queue;

  @Value("${app.rabbitmq.routing-key}")
  private String routingKey;

  @Bean
  public DirectExchange orderExchange() {
    return new DirectExchange(exchange);
  }

  @Bean
  public Queue orderCreatedQueue() {
    return new Queue(queue, true);
  }

  @Bean
  public Binding orderCreatedBinding() {
    return BindingBuilder.bind(orderCreatedQueue()).to(orderExchange()).with(routingKey);
  }

  @Bean
  public Gson gson() {
    return new Gson();
  }

  @Bean
  public MessageConverter messageConverter(Gson gson) {
    return new MessageConverter() {

      @Override
      public Message toMessage(Object object, MessageProperties messageProperties)
          throws MessageConversionException {

        String json = gson.toJson(object);

        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());

        return new Message(json.getBytes(StandardCharsets.UTF_8), messageProperties);
      }

      @Override
      public Object fromMessage(Message message) throws MessageConversionException {

        String json = new String(message.getBody(), StandardCharsets.UTF_8);

        return gson.fromJson(json, Object.class);
      }
    };
  }

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(messageConverter);

    return template;
  }
}
