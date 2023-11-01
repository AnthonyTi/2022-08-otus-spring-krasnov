package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.domain.Order;

@Service
@Slf4j
public class CollectingOrderItemsServiceImpl implements CollectingOrderItemsService {

    @Override
    public Order collectItems(Order order) {
        order.getOrderItems().add("get some bonuses :)");
        log.info("The order collected.");
        return order;
    }

}
