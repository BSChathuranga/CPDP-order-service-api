package com.decstack.quickcart.order_service_api.repo;

import com.decstack.quickcart.order_service_api.entitiy.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetaiRepo extends JpaRepository <OrderDetail, String> {
}
