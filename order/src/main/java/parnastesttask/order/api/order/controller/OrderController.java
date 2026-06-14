package parnastesttask.order.api.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parnastesttask.order.api.order.controller.dto.CreateOrderRequest;
import parnastesttask.order.api.order.controller.dto.OrderResponse;
import parnastesttask.order.api.order.controller.dto.UpdateStatusRequest;

@RequiredArgsConstructor
@RequestMapping("/api/orders")
@RestController
@Tag(name = "order")
public class OrderController {

  @Operation(summary = "создать заказ")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "заказ создан"),
    @ApiResponse(responseCode = "400", description = "ошибка валидации"),
    @ApiResponse(responseCode = "500", description = "неизвестная ошибка")
  })
  @PostMapping
  public ResponseEntity<OrderResponse> create(
      @Valid @RequestBody CreateOrderRequest createOrderRequest) {
    // TODO
    OrderResponse orderResponse = OrderResponse.builder().build();

    return ResponseEntity.status(201).body(orderResponse);
  }

  @Operation(summary = "список с фильтром status")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "список заказов"),
    @ApiResponse(responseCode = "400", description = "ошибка валидации"),
    @ApiResponse(responseCode = "500", description = "неизвестная ошибка")
  })
  @GetMapping
  public ResponseEntity<List<OrderResponse>> get(
      @RequestParam String status,
      @RequestParam int page,
      @RequestParam int size,
      @RequestParam String sort) {
    // TODO
    List<OrderResponse> orderResponses = new ArrayList<>();

    return ResponseEntity.ok().body(orderResponses);
  }

  @Operation(summary = "заказ со всеми позициями")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "данные заказа со всеми позициями"),
    @ApiResponse(responseCode = "404", description = "заказ с таким id не существует"),
    @ApiResponse(responseCode = "500", description = "неизвестная ошибка")
  })
  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> getById(@PathVariable UUID id) {
    OrderResponse orderResponse = OrderResponse.builder().build();

    return ResponseEntity.ok(orderResponse);
  }

  @Operation(summary = "обновить статус заказа")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "данные пользователя получены"),
    @ApiResponse(responseCode = "400", description = "ошибка валидации"),
    @ApiResponse(responseCode = "404", description = "заказ с таким id не существует"),
    @ApiResponse(responseCode = "500", description = "неизвестная ошибка")
  })
  @PutMapping("/{id}/status")
  public ResponseEntity<OrderResponse> updateStatus(
      @PathVariable UUID id, @Valid @RequestBody UpdateStatusRequest updateStatusRequest) {
    OrderResponse orderResponse = OrderResponse.builder().build();

    return ResponseEntity.ok(orderResponse);
  }
}
