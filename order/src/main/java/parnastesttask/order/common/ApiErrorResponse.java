package parnastesttask.order.common;

import java.time.Instant;

public record ApiErrorResponse(
    String message, Instant timestamp, int status, String error, String path) {}
