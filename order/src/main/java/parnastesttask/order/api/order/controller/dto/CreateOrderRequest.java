package parnastesttask.order.api.order.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateOrderRequest(
    @NotBlank String customerName, @NotEmpty @Valid List<CreateOrderItemRequest> items) {}
