package com.bridgelabz.book.store.app.backend.repository;

import com.bridgelabz.book.store.app.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> findByUserIdAndCancel(long userId, boolean cancel);
    Order findByUserIdAndOrderId(long userId, int orderId);
    List<Order> findByCancel(boolean cancel);
}
