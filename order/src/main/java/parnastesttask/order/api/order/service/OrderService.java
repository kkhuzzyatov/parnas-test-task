package parnastesttask.order.api.order.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parnastesttask.order.api.order.model.Order;
import parnastesttask.order.api.order.repository.OrderRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

  private final OrderRepository orderRepository;

  public Order create(Order order) {
    return orderRepository.save(order);
  }

  @Transactional(readOnly = true)
  public List<Order> getByStatus(Order.Status status, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Order> resultPage = orderRepository.findAllByStatus(status, pageable);
    return resultPage.getContent();
  }

  @Transactional(readOnly = true)
  public Order getWithItemsById(UUID id) {
    return orderRepository
        .findWithItemsById(id)
        .orElseThrow(() -> new EntityNotFoundException("Order not found"));
  }

  public Order updateStatus(UUID id, Order.Status status) {
    Order order =
        orderRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));

    order.changeStatus(status);

    return orderRepository.save(order);
  }
}
