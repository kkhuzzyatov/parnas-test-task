package parnastesttask.order.api.order.controller.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record OrderWithoutDetailResponse(
    String orderId, String customerName, LocalDateTime orderDate, String status) {}
