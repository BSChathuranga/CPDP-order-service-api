package com.decstack.quickcart.order_service_api.service.impl;



import com.decstack.quickcart.order_service_api.dto.request.CustomerOrderRequestDto;
import com.decstack.quickcart.order_service_api.dto.request.OrderDetailRequestDto;
import com.decstack.quickcart.order_service_api.dto.response.CustomerOrderResponseDto;
import com.decstack.quickcart.order_service_api.dto.response.paginate.CustomerOrderPaginateDto;
import com.decstack.quickcart.order_service_api.entitiy.CustomerOrder;
import com.decstack.quickcart.order_service_api.entitiy.OrderDetail;
import com.decstack.quickcart.order_service_api.entitiy.OrderStatus;
import com.decstack.quickcart.order_service_api.exception.EntryNotFoundException;
import com.decstack.quickcart.order_service_api.repo.CustomerOrderRepo;
import com.decstack.quickcart.order_service_api.repo.OrderStatusRepo;
import com.decstack.quickcart.order_service_api.service.CustomerOrderService;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepo customerOrderRepo;
    private final OrderStatusRepo orderStatusRepo;



    @Override
    public void createOrder(CustomerOrderRequestDto requestDto) {
        OrderStatus orderStatus = orderStatusRepo.findByStatus("PENDING").orElseThrow(()-> new EntryNotFoundException("Order Status Not Found. so you can't place an order please contact admin"));

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderId(UUID.randomUUID().toString());
        customerOrder.setOrderDate(requestDto.getOrderDate());
        customerOrder.setRemark("");
        customerOrder.setTotalAmount(requestDto.getTotalAmount());
        customerOrder.setUserId(requestDto.getUserId());
        customerOrder.setOrderStatus(orderStatus);
        customerOrder.setProducts(
                requestDto.getOrderDetails().stream().map(e ->createOrderDetail(e,customerOrder)).collect(Collectors.toSet())
        );
        customerOrderRepo.save(customerOrder);

    }

    @Override
    public void updateOrder(CustomerOrderRequestDto requestDto,String orderId) {
        CustomerOrder customerOrder =
                customerOrderRepo.findById(orderId).orElseThrow(()->new EntryNotFoundException(String.format("Order not Found with %s", orderId)));
        customerOrder.setOrderDate(requestDto.getOrderDate());
        customerOrder.setTotalAmount(requestDto.getTotalAmount());
        customerOrderRepo.save(customerOrder);
    }

    @Override
    public void manageRemark(String remark,String orderId) {
        CustomerOrder customerOrder =
                customerOrderRepo.findById(orderId).orElseThrow(()->new EntryNotFoundException(String.format("Order not Found with %s", orderId)));
        customerOrder.setRemark(remark);
        customerOrderRepo.save(customerOrder);

    }

    @Override
    public void manageStatus(String status,String orderId) {
        CustomerOrder customerOrder =
                customerOrderRepo.findById(orderId).orElseThrow(()->new RuntimeException(String.format("Order not Found with %s", orderId)));
        OrderStatus orderStatus = orderStatusRepo.findByStatus(status).orElseThrow(()-> new EntryNotFoundException("Order Status Not Found. so you can't place an order please contact admin"));
        customerOrder.setOrderStatus(orderStatus);
        customerOrderRepo.save(customerOrder);
    }





    @Override
    public CustomerOrderResponseDto findOrderById(String orderId) {
       CustomerOrder customerOrder =
               customerOrderRepo.findById(orderId).orElseThrow(()->new EntryNotFoundException(String.format("Order not Found with %s", orderId)));
        return toCustomerOrderResponseDto( customerOrder );
    }
    @Override
    public void deleteById(String orderId) {
        CustomerOrder customerOrder =
                customerOrderRepo.findById(orderId).orElseThrow(() -> new EntryNotFoundException(String.format("Order not Found with %s", orderId)));
        customerOrderRepo.delete(customerOrder);

    }
    @Override
    public CustomerOrderPaginateDto searchAll(String searchText, int page, int size) {
        return CustomerOrderPaginateDto.builder()
                .count(
                        customerOrderRepo.searchCount(searchText)
                )
                .dataList(
                        customerOrderRepo.searchAll(searchText, PageRequest.of(page,size))
                                .stream().map(this::toCustomerOrderResponseDto).collect(Collectors.toList())
                )

                .build();
    }

    private CustomerOrderResponseDto toCustomerOrderResponseDto(CustomerOrder customerOrder) {
        if (customerOrder == null) {
            return null;
        }
         return CustomerOrderResponseDto.builder()
                .orderId(customerOrder.getOrderId())
                .orderDate(customerOrder.getOrderDate())
                .userId(customerOrder.getUserId())
                .totalAmount(customerOrder.getTotalAmount())
                .orderDetails(
                        customerOrder.getProducts().stream().map(this::toOrderDetailResponseDto).collect(Collectors.toList())
                )
                .remark(customerOrder.getRemark())
                .status(customerOrder.getOrderStatus().getStatus())
                .build();
    }
    private OrderDetailRequestDto toOrderDetailResponseDto(OrderDetail orderDetail) {
        if (orderDetail == null) {
            return null;
        }
        return OrderDetailRequestDto.builder()
                .productId(orderDetail.getProductId())
                .detailId(orderDetail.getDetail_id())
                .discount(orderDetail.getDiscount())
                .qty(orderDetail.getQty())
                .unitprice(orderDetail.getUnitprice())
                .build();
    }

    private OrderDetail createOrderDetail(OrderDetailRequestDto requestDto, CustomerOrder order) {
        if (requestDto == null) {
            return null;
        }
        return OrderDetail.builder()
                .detail_id(UUID.randomUUID().toString())
                .unitprice(requestDto.getUnitprice())
                .discount(requestDto.getDiscount())
                .qty(requestDto.getQty())
                .customerOrder(order)
                .build();


    }
}
