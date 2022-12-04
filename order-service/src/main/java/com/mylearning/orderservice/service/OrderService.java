package com.mylearning.orderservice.service;

import com.mylearning.orderservice.dto.InventoryResponse;
import com.mylearning.orderservice.dto.OrderLineItemsDto;
import com.mylearning.orderservice.dto.OrderRequest;
import com.mylearning.orderservice.model.Order;
import com.mylearning.orderservice.model.OrderLineItems;
import com.mylearning.orderservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    //private String inventoryUrl="http:'//localhost:8088/api/inventory";

    private String inventoryUrl="http://INVENTORY-SERVICE/api/inventory";

    //@Autowired
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest) {
        /*
        placeOrder(orderRequest) :: accepting orderrequest dto which contains list of OrderLineItemsDto
        to extract OrderLineItemsDto and then set OrderLineItems using builder pattern and setting it to order
        to get the list of skucodes of each OrderLineItems to use skucodes to check and verify and validate availabality of product
        with the Inventory-Service before placing the order using InventoryResponse as dto here in Order-Service
         */
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                        .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItems);

        // CALLING INVENTORY-SERVICE AND PLACING ORDER IF PRODUCT IS-IN-STOCK

        // collecting all the skucodes from the OrderLineItems of order in order to use it to verify the availabality of product by getting the response from Inventory-Service
        List<String> skuCodes=order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());

        // getting response from Inventory-Service to collect isInStock details for each OrderLineItems of an order
        InventoryResponse[] responses=webClientBuilder.build().get()
                        .uri(inventoryUrl,
                                uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                                .retrieve()
                                        .bodyToMono(InventoryResponse[].class)
                                                .block();

        // when all the products of the order we place isInStock is true in InventoryResponse then only below line of code will return true
        boolean allProductsInStock= Arrays.stream(responses).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
            return "Order Placed Successfully";
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    // mapping the OrderLineItemsDto to OrderLineItems
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
