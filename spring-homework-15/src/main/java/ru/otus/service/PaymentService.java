package ru.otus.service;

import ru.otus.domain.Order;

public interface PaymentService {

    Order getPayment(Order order);
}
