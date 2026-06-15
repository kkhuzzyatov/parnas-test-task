package parnastesttask.order.messaging;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderCreatedEvent(UUID orderId, String customerName, BigDecimal totalAmount) {}
