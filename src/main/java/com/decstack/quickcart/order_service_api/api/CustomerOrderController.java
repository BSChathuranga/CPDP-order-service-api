package com.decstack.quickcart.order_service_api.api;

import com.decstack.quickcart.order_service_api.dto.request.CustomerOrderRequestDto;
import com.decstack.quickcart.order_service_api.entitiy.CustomerOrder;
import com.decstack.quickcart.order_service_api.service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer-orders")
@RequiredArgsConstructor

public class CustomerOrderController {
    private final CustomerOrderService customerOrderService;

    @PostMapping("/bussiness")
    public String create(@RequestBody CustomerOrderRequestDto request) {
        customerOrderService.createOrder(request);
    }

    @GetMapping("/visitors/find-by-id/{id}")
    public String create(@PathVariable String id) {
        customerOrderService.findOrderById(id);
    }
    @PutMapping ("/bussiness/update-order/{id}")
    public String create(@RequestBody CustomerOrderRequestDto request, @PathVariable String id) {
        customerOrderService.updateOrder(request, id);
    }

    @PutMapping ("/bussiness/update-remark/{id}")
    public String manageRemark(@RequestParam String remark, @PathVariable String id) {
        customerOrderService.manageRemark(remark, id);
    }

    @PutMapping ("/bussiness/update-status/{id}")
    public String manageStatus(@RequestBody CustomerOrderRequestDto status, @PathVariable String id) {
        customerOrderService.manageStatus(status, id);
    }

    @DeleteMapping ("/bussiness/delete-by-id/{id}")
    public String deleteById(@PathVariable String id) {
        customerOrderService.deleteById(id);
    }
    @GetMapping("/visitors/search-all/{id}")
    public String searchAll(@RequestParam String searchText, @RequestParam int page, @RequestParam int size) {
        customerOrderService.searchAll(searchText,page,size);
    }

}