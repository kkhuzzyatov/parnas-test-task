package parnastesttask.order.api.order.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import parnastesttask.order.api.order.model.Order;
import parnastesttask.order.api.order.repository.OrderRepository;
import parnastesttask.order.messaging.OrderEventProducer;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock private OrderRepository orderRepository;

  @Mock private OrderEventProducer orderEventProducer;

  @InjectMocks private OrderService orderService;

  @Test
  void create_shouldSaveOrder() {
    Order order = Order.builder().customerName("test").status(Order.Status.CREATED).build();

    when(orderRepository.save(order)).thenReturn(order);

    Order result = orderService.create(order);

    assertEquals(order, result);
    verify(orderRepository).save(order);
    verify(orderEventProducer).sendOrderCreatedEvent(order);
  }

  @Test
  void getWithItemsById_shouldReturnOrder() {
    UUID id = UUID.randomUUID();

    Order order = Order.builder().id(id).status(Order.Status.CREATED).build();

    when(orderRepository.findWithItemsById(id)).thenReturn(Optional.of(order));

    Order result = orderService.getWithItemsById(id);

    assertEquals(order, result);
  }

  @Test
  void getWithItemsById_shouldThrow_whenNotFound() {
    UUID id = UUID.randomUUID();

    when(orderRepository.findWithItemsById(id)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> orderService.getWithItemsById(id));
  }

  @Test
  void updateStatus_shouldChangeStatus() {
    UUID id = UUID.randomUUID();

    Order order = Order.builder().id(id).status(Order.Status.CREATED).build();

    when(orderRepository.findById(id)).thenReturn(Optional.of(order));
    when(orderRepository.save(order)).thenReturn(order);

    Order result = orderService.updateStatus(id, Order.Status.PROCESSING);

    assertEquals(Order.Status.PROCESSING, result.getStatus());
  }

  @Test
  void getByStatus_shouldReturnPageContent() {
    Order order = Order.builder().status(Order.Status.CREATED).build();

    Page<Order> page = new PageImpl<>(List.of(order));

    when(orderRepository.findAllByStatus(eq(Order.Status.CREATED), any(PageRequest.class)))
        .thenReturn(page);

    List<Order> result = orderService.getByStatus(Order.Status.CREATED, 0, 10);

    assertEquals(1, result.size());
  }
}
