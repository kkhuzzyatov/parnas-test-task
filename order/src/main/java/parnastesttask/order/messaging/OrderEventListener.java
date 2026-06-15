package parnastesttask.order.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import parnastesttask.order.api.order.model.Order;
import parnastesttask.order.api.order.service.OrderService;

@RequiredArgsConstructor
@Component
public class OrderEventListener {
    private final OrderService orderService;

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void handleOrderCreated(OrderCreatedEvent event) {
        orderService.updateStatus(event.orderId(), Order.Status.PROCESSING);

        System.out.println(String.format("Order status updated to PROCESSING: orderId=%s", event.orderId()));
    }
}
