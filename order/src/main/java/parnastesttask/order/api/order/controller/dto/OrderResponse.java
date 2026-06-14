package parnastesttask.order.api.order.controller.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record OrderResponse(String orderId, String customerName, List<OrderItemResponse> items) {}
