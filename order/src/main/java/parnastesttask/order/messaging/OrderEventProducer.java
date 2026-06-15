package parnastesttask.order.messaging;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import parnastesttask.order.api.order.model.Order;

@RequiredArgsConstructor
@Component
public class OrderEventProducer {
  private final RabbitTemplate rabbitTemplate;

  @Value("${app.rabbitmq.exchange}")
  private String exchange;

  @Value("${app.rabbitmq.routing-key}")
  private String routingKey;

  public void sendOrderCreatedEvent(Order order) {
    BigDecimal totalAmount =
        order.getItems().stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    OrderCreatedEvent orderCreatedEvent =
        OrderCreatedEvent.builder()
            .orderId(order.getId())
            .customerName(order.getCustomerName())
            .totalAmount(totalAmount)
            .build();

    rabbitTemplate.convertAndSend(exchange, routingKey, orderCreatedEvent);
  }
}
