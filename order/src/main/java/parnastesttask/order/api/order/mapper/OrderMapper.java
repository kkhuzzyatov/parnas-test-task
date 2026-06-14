package parnastesttask.order.api.order.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import parnastesttask.order.api.order.controller.dto.CreateOrderItemRequest;
import parnastesttask.order.api.order.controller.dto.CreateOrderRequest;
import parnastesttask.order.api.order.controller.dto.OrderItemResponse;
import parnastesttask.order.api.order.controller.dto.OrderResponse;
import parnastesttask.order.api.order.model.Order;
import parnastesttask.order.api.order.model.OrderItem;

@Component
@RequiredArgsConstructor
public class OrderMapper {

  public Order toEntity(CreateOrderRequest request) {
    Order order =
        Order.builder().customerName(request.customerName()).status(Order.Status.CREATED).build();

    List<OrderItem> items = request.items().stream().map(this::toEntity).toList();

    items.forEach(order::addItem);

    return order;
  }

  public OrderItem toEntity(CreateOrderItemRequest request) {
    return OrderItem.builder()
        .productName(request.productName())
        .quantity(request.quantity())
        .price(request.price())
        .build();
  }

  public OrderResponse toResponse(Order order) {
    return OrderResponse.builder()
        .orderId(order.getId().toString())
        .customerName(order.getCustomerName())
        .items(order.getItems().stream().map(this::toResponse).toList())
        .build();
  }

  public OrderItemResponse toResponse(OrderItem orderItem) {
    return OrderItemResponse.builder()
        .orderItemId(orderItem.getId())
        .productName(orderItem.getProductName())
        .quantity(orderItem.getQuantity())
        .price(orderItem.getPrice())
        .build();
  }
}
