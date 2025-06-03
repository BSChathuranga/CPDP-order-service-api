package com.decstack.quickcart.order_service_api.dto.request;


import lombok.*;

import java.util.ArrayList;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderDetailRequestDto {
    private String detailId;
    private String productId;
    private int qty;
    private double unitprice;
    private double discount;


}
