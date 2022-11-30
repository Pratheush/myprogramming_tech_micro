package com.mylearning.orderservice.service;

import com.mylearning.orderservice.dto.InventoryResponse;
import com.mylearning.orderservice.dto.OrderLineItemsDto;
import com.mylearning.orderservice.dto.OrderRequest;
import com.mylearning.orderservice.model.Order;
import com.mylearning.orderservice.model.OrderLineItems;
import com.mylearning.orderservice.repository.OrderRepository;
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

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                        .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItems);

        // call Inventory-service and place order if product is in stock
        List<String> skuCodes=order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());

        InventoryResponse[] responses=webClientBuilder.build().get()
                        .uri(inventoryUrl,
                                uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                                .retrieve()
                                        .bodyToMono(InventoryResponse[].class)
                                                .block();

        // when all the products isInStock is true in InventoryResponse then only below line of code will return true
        boolean allProductsInStock= Arrays.stream(responses).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
