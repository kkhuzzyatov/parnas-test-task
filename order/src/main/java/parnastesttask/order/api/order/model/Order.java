package parnastesttask.order.api.order.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Setter(AccessLevel.NONE)
  private UUID id;

  @Column(name = "customer_name", nullable = false)
  private String customerName;

  @Column(name = "order_date", nullable = false)
  @Setter(AccessLevel.NONE)
  private LocalDateTime orderDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

  @OneToMany(
          fetch = FetchType.LAZY,
          mappedBy = "order",
          cascade = CascadeType.ALL,
          orphanRemoval = true)
  @Builder.Default
  @Setter(AccessLevel.NONE)
  private List<OrderItem> items = new ArrayList<>();

  public enum Status {
    CREATED,
    PROCESSING,
    COMPLETED,
    CANCELED
  }

  public void changeCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public void changeStatus(Status status) {
    this.status = status;
  }

  public void addItem(OrderItem item) {
    items.add(item);
    item.setOrder(this);
  }

  public void removeItem(OrderItem item) {
    items.remove(item);
    item.setOrder(null);
  }
}