package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.domain.Order;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Override
    public Order getPayment(Order order) {
        log.info("Payment complete");
        return order;
    }

}
