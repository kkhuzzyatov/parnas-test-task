package parnastesttask.order.api.order.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import parnastesttask.order.api.order.controller.dto.CreateOrderItemRequest;
import parnastesttask.order.api.order.controller.dto.CreateOrderRequest;
import parnastesttask.order.api.order.controller.dto.OrderDetailResponse;
import parnastesttask.order.api.order.controller.dto.OrderItemResponse;
import parnastesttask.order.api.order.controller.dto.OrderWithoutDetailResponse;
import parnastesttask.order.api.order.model.Order;
import parnastesttask.order.api.order.model.OrderItem;

@Component
public class OrderMapper {

  public Order toEntity(CreateOrderRequest request) {
    Order order =
        Order.builder().customerName(request.customerName()).status(Order.Status.CREATED).build();

    request.items().stream().map(this::toEntity).forEach(order::addItem);

    return order;
  }

  public OrderItem toEntity(CreateOrderItemRequest request) {
    return OrderItem.builder()
        .productName(request.productName())
        .quantity(request.quantity())
        .price(request.price())
        .build();
  }

  public OrderWithoutDetailResponse toListResponse(Order order) {
    return OrderWithoutDetailResponse.builder()
        .orderId(order.getId().toString())
        .customerName(order.getCustomerName())
        .orderDate(order.getOrderDate())
        .status(order.getStatus().name())
        .build();
  }

  public List<OrderWithoutDetailResponse> toListResponse(List<Order> orders) {
    return orders.stream().map(this::toListResponse).toList();
  }

  public OrderDetailResponse toDetailResponse(Order order) {
    return OrderDetailResponse.builder()
        .orderId(order.getId().toString())
        .customerName(order.getCustomerName())
        .orderDate(order.getOrderDate())
        .status(order.getStatus().name())
        .items(toItemResponses(order.getItems()))
        .build();
  }

  public OrderItemResponse toItemResponse(OrderItem item) {
    return OrderItemResponse.builder()
        .orderItemId(item.getId())
        .productName(item.getProductName())
        .quantity(item.getQuantity())
        .price(item.getPrice())
        .build();
  }

  public List<OrderItemResponse> toItemResponses(List<OrderItem> items) {
    return items.stream().map(this::toItemResponse).toList();
  }
}
