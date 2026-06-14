package parnastesttask.order.api.order.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record CreateOrderItemRequest(
    @NotBlank String productName, @NotNull @Min(1) Integer quantity, @DecimalMin("0.01") BigDecimal price) {}
