package com.decstack.quickcart.order_service_api.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OrderDetailRequestDto {
    private String ProductId;
    private int qty;
    private String totalAmount;
    private ArrayList<OrderDetailRequestDto> orderDetails;


}
