create table orders (
    order_id uuid primary key,
    customer_name varchar(255) not null,
    order_date timestamp not null,
    status varchar(20) not null,

    constraint chk_order_status
        check (status in ('CREATED', 'PROCESSING', 'COMPLETED', 'CANCELED'))
);

create table order_item (
    order_item_id bigserial primary key,
    product_name varchar(255) not null,
    quantity integer not null,
    price decimal(19, 2) not null,

    order_id uuid not null,

    constraint fk_order
        foreign key (order_id)
        references orders(order_id)
        on delete cascade
);

create index idx_orders_status on orders(status);
create index idx_orders_customer_name on orders(customer_name);