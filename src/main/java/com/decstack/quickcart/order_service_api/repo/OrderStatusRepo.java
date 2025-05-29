package com.decstack.quickcart.order_service_api.repo;

import com.decstack.quickcart.order_service_api.entitiy.OrderDetail;
import com.decstack.quickcart.order_service_api.entitiy.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderStatusRepo extends JpaRepository<OrderDetail, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM order_status WHERE status=?! ")
    public Optional<OrderStatus> findByStatus(String status);
}
