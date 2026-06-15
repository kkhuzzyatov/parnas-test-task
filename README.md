### Выполнено
- Модель данных: Order, OrderItem
- Liquibase миграции с индексами
- REST API: POST, GET, GET/{id}, PUT/{id}/status
- Корректные HTTP статусы (200, 201, 400, 404) и сообщения об ошибках
- Spring Data JPA + кастомный @Query с JOIN и агрегацией
- RabbitMQ: producer, listener, конфиг обменника/очереди/биндинга
- Swagger UI / OpenAPI документация
- Unit тесты сервиса (Mockito)
- Интеграционные тесты (MockMvc + Testcontainers)

---

## Запуск

[build](./build.sh)