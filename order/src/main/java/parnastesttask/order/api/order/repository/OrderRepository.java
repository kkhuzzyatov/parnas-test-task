package parnastesttask.order.api.order.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import parnastesttask.order.api.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

  Page<Order> findAllByStatus(Order.Status status, Pageable pageable);

  @EntityGraph(attributePaths = "items")
  Optional<Order> findWithItemsById(UUID id);
}
