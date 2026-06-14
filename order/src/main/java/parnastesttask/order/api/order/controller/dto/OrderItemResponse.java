package parnastesttask.order.api.order.controller.dto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record OrderItemResponse(
    String productName, Long orderItemId, Integer quantity, BigDecimal price) {}
