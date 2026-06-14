package parnastesttask.order;

import org.springframework.boot.SpringApplication;

public class TestOrderApplication {

  static void main(String[] args) {
    SpringApplication.from(OrderApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
