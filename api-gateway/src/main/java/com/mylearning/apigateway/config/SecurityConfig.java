package com.mylearning.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

//spring-cloud-gateway project is based on webflux project but not spring-webmvc so that's why we need @EnableWebFLuxSecurity at the configuration class
//of gateway springboot application along with @SpringBoot Application. marked on the main class.
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    // defining the  security-filter-chain for implementing the security to the incoming request to the api-gateway and based on each request matching path incoming request will be authenticated
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        /*
        here /eureka/** matching path predicate will be permitted i.e. when retrieving the css , javascript and all other resources of eureka will be permitted and will not be authenticated
        as we don't want to send some kind of token with the request for css , javascript of eureka so fot that we are excluding the calls regarding those using /eureka/**
        and all other incoming calls or incoming request through api-gateway will be authenticated
         */

        serverHttpSecurity
                .csrf().disable() // we are disabling it as we are communicating through resp-api through the postman client or we do curl
                .authorizeExchange(exchange ->  // we are exchanging information by permiting all the matching "/eureka/**" request and all other requests to other micro-services through api-gateway will be authenticated using authorization-server(Keycloak)
                        exchange.pathMatchers("/eureka/**")
                                .permitAll()
                                .anyExchange()
                                .authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt); // enabling the resource-server capabilities and enabling the json-web-token capability
        return serverHttpSecurity.build(); // creating the security-web-filter-chain
    }
}