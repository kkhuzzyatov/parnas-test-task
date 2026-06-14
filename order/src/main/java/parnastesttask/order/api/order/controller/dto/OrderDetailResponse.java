package parnastesttask.order.api.order.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderDetailResponse(
    String orderId,
    String customerName,
    LocalDateTime orderDate,
    String status,
    List<OrderItemResponse> items) {}
