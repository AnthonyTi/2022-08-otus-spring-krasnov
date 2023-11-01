package ru.otus.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.domain.Order;

@MessagingGateway
public interface ServiceGateway {

    @Gateway(requestChannel = "orderChannel", replyChannel = "customerChannel")
    Order process(Order order);

}
