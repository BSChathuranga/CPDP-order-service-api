package com.decstack.quickcart.order_service_api.repo;

import com.decstack.quickcart.order_service_api.entitiy.CustomerOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface CustomerOrderRepo extends JpaRepository<CustomerOrder, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM customer_order WHERE remark LIKE %?!%")
    public Page<CustomerOrder> searchAll(String remark, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT COUNT(order_id) FROM customer_order WHERE remark LIKE %?!%")
    public long searchCount(String remark);



}


