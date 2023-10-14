package ru.otus.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.domain.Order;

@Service
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    @Override
    @SneakyThrows
    public Order deliverOrder(Order order) {

        Thread.sleep(RandomUtils.nextInt(1000, 3000));
        log.info("The order delivered: " + order.getOrderItems());
        return order;
    }

}
