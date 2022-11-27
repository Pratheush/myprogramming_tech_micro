# _**spring-boot-microservices-new**_
This repository contains the latest source code of the spring-boot-microservices tutorial

## _**Product Service**_ :: Create and View Products acts as Product Catalog

## **_Order Service_** :: Can Order Products. Order Service communicates(synchronous communication) Inventory Service to check whether inventory is available or not before placing the order by making synchronous communication. Once the order is being placed asynchronous communication with the Notification Service

## **_Inventory Service_** :: Can Check if product is in stock or not

## **_**Notification Service**_** :: Stateless service which Can send notifications to users, after order is placed

_**Order Service, Inventory Service , Notification Service are going to interact with each other :: communicating with each other synchronous and asynchronous communication**_.
