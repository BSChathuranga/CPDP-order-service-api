package com.decstack.quickcart.order_service_api.repo;

import com.decstack.quickcart.order_service_api.entitiy.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepo extends JpaRepository<CustomerOrder, String> {
}
