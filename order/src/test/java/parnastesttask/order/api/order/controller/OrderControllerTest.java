package parnastesttask.order.api.order.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import parnastesttask.order.api.order.controller.dto.CreateOrderItemRequest;
import parnastesttask.order.api.order.controller.dto.CreateOrderRequest;
import parnastesttask.order.api.order.controller.dto.UpdateStatusRequest;
import parnastesttask.order.api.order.mapper.OrderMapper;
import parnastesttask.order.api.order.model.Order;
import parnastesttask.order.api.order.service.OrderService;
import parnastesttask.order.common.ApiExceptionHandler;

public class OrderControllerTest {

  private MockMvc mockMvc;
  private OrderService orderService;
  private OrderMapper orderMapper;
  private ObjectMapper objectMapper;

  private final UUID orderId = UUID.randomUUID();

  @BeforeEach
  void setup() {
    orderService = mock(OrderService.class);
    orderMapper = new OrderMapper();
    objectMapper = new ObjectMapper();

    OrderController controller = new OrderController(orderService, orderMapper);

    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new ApiExceptionHandler())
            .build();

    reset(orderService);
  }

  @Test
  void shouldCreateOrderSuccessfully() throws Exception {
    CreateOrderRequest request =
        new CreateOrderRequest(
            "John", List.of(new CreateOrderItemRequest("item1", 2, new BigDecimal("10.00"))));

    Order saved =
        Order.builder().id(orderId).customerName("John").status(Order.Status.CREATED).build();

    when(orderService.create(any(Order.class))).thenReturn(saved);

    mockMvc
        .perform(
            post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());

    verify(orderService).create(any(Order.class));
  }

  @Test
  void shouldReturnOrdersByStatus() throws Exception {
    Order order =
        Order.builder().id(orderId).customerName("John").status(Order.Status.CREATED).build();

    when(orderService.getByStatus(eq(Order.Status.CREATED), eq(0), eq(10)))
        .thenReturn(List.of(order));

    mockMvc
        .perform(
            get("/api/orders")
                .param("status", "CREATED")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,asc"))
        .andExpect(status().isOk());

    verify(orderService).getByStatus(Order.Status.CREATED, 0, 10);
  }

  @Test
  void shouldReturnOrderById() throws Exception {
    Order order =
        Order.builder().id(orderId).customerName("John").status(Order.Status.CREATED).build();

    when(orderService.getWithItemsById(orderId)).thenReturn(order);

    mockMvc.perform(get("/api/orders/{id}", orderId)).andExpect(status().isOk());

    verify(orderService).getWithItemsById(orderId);
  }

  @Test
  void shouldUpdateStatusSuccessfully() throws Exception {
    UpdateStatusRequest request = new UpdateStatusRequest("PROCESSING");

    Order updated =
        Order.builder().id(orderId).customerName("John").status(Order.Status.PROCESSING).build();

    when(orderService.updateStatus(orderId, Order.Status.PROCESSING)).thenReturn(updated);

    mockMvc
        .perform(
            put("/api/orders/{id}/status", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

    verify(orderService).updateStatus(orderId, Order.Status.PROCESSING);
  }

  @Test
  void shouldReturnBadRequest_whenCreateBodyInvalid() throws Exception {
    CreateOrderRequest request = new CreateOrderRequest("", List.of());

    mockMvc
        .perform(
            post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

    verifyNoInteractions(orderService);
  }

  @Test
  void shouldReturnNotFound_whenOrderMissing() throws Exception {
    when(orderService.getWithItemsById(orderId))
        .thenThrow(new EntityNotFoundException("Order not found"));

    mockMvc.perform(get("/api/orders/{id}", orderId)).andExpect(status().isNotFound());

    verify(orderService).getWithItemsById(orderId);
  }
}
