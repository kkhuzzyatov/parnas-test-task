package parnastesttask.order.api.order.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateStatusRequest(
    @NotBlank
        @Pattern(
            regexp = "CREATED|PROCESSING|COMPLETED|CANCELED",
            message = "status must be one of: CREATED, PROCESSING, COMPLETED, CANCELED")
        String status) {}
