package parnastesttask.order.api.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parnastesttask.order.api.order.controller.dto.CreateOrderRequest;
import parnastesttask.order.api.order.controller.dto.OrderDetailResponse;
import parnastesttask.order.api.order.controller.dto.OrderWithoutDetailResponse;
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
  public ResponseEntity<OrderDetailResponse> create(
      @Valid @RequestBody CreateOrderRequest request) {

    // TODO service call
    return ResponseEntity.status(201).build();
  }

  @Operation(summary = "список заказов с фильтром status + пагинация")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "список заказов"),
    @ApiResponse(responseCode = "400", description = "ошибка валидации"),
    @ApiResponse(responseCode = "500", description = "неизвестная ошибка")
  })
  @GetMapping
  public ResponseEntity<List<OrderWithoutDetailResponse>> get(
      @RequestParam String status,
      @RequestParam int page,
      @RequestParam int size,
      @RequestParam String sort) {

    // TODO service call
    return ResponseEntity.ok(List.of());
  }

  @Operation(summary = "заказ со всеми позициями")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "данные заказа"),
    @ApiResponse(responseCode = "404", description = "заказ не найден"),
    @ApiResponse(responseCode = "500", description = "неизвестная ошибка")
  })
  @GetMapping("/{id}")
  public ResponseEntity<OrderDetailResponse> getById(@PathVariable UUID id) {

    // TODO service call
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "обновить статус заказа")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "статус обновлён"),
    @ApiResponse(responseCode = "400", description = "ошибка валидации"),
    @ApiResponse(responseCode = "404", description = "заказ не найден"),
    @ApiResponse(responseCode = "500", description = "неизвестная ошибка")
  })
  @PutMapping("/{id}/status")
  public ResponseEntity<OrderDetailResponse> updateStatus(
      @PathVariable UUID id, @Valid @RequestBody UpdateStatusRequest request) {

    // TODO service call
    return ResponseEntity.ok().build();
  }
}
