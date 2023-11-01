package ru.otus.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.domain.Order;
import ru.otus.gateway.ServiceGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Service
@Slf4j
public class StoreServiceImpl implements StoreService {

    static final List<String> ORDER_ITEMS = List.of("milk", "apple", "cereal", "meat", "rice", "buckwheat");

    private final ServiceGateway serviceGateway;

    public StoreServiceImpl(ServiceGateway serviceGateway) {
        this.serviceGateway = serviceGateway;
    }

    @SneakyThrows
    @Override
    public void makeOrders() {

        ForkJoinPool pool = ForkJoinPool.commonPool();

        while (true) {
            Thread.sleep(RandomUtils.nextInt(2000, 5000));

            pool.execute(() -> {
                Order order = generateOrder();
                log.info("New order received: " + order.getOrderItems());
                serviceGateway.process(order);
                log.info("The order has been processed!");
            });
        }

    }

    private static Order generateOrder() {
        List<String> orderItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            orderItems.add(ORDER_ITEMS.get(RandomUtils.nextInt(0, ORDER_ITEMS.size())));
        }
        return new Order(orderItems);
    }
}
