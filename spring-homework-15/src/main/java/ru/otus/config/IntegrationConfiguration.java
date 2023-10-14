package ru.otus.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.util.ErrorHandler;
import ru.otus.service.CollectingOrderItemsService;
import ru.otus.service.DeliveryService;
import ru.otus.service.PaymentService;

@Configuration
@Slf4j
public class IntegrationConfiguration {

    private final static Integer CAPACITY_QUEUE = 10;

    private final static Integer POLLER_PERIOD = 100;

    private final static Integer MESSAGES_PER_POLL = 2;

    private final PaymentService paymentService;

    private final CollectingOrderItemsService collectingOrderItemsService;

    private final DeliveryService deliveryService;

    public IntegrationConfiguration(PaymentService paymentService, CollectingOrderItemsService collectingOrderItemsService, DeliveryService deliveryService) {
        this.paymentService = paymentService;
        this.collectingOrderItemsService = collectingOrderItemsService;
        this.deliveryService = deliveryService;
    }

    @Bean
    public QueueChannel orderChannel() {
        return MessageChannels.queue(CAPACITY_QUEUE).get();
    }

    @Bean
    public PublishSubscribeChannel customerChannel() {
        return MessageChannels.publishSubscribe().errorHandler(t -> log.info("The order will not be processed")).get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(POLLER_PERIOD).maxMessagesPerPoll(MESSAGES_PER_POLL).get();
    }


    @Bean
    public IntegrationFlow orderFlow() {
        return IntegrationFlows.from("orderChannel")
                .handle(paymentService, "getPayment")
                .handle(collectingOrderItemsService, "collectItems")
                .handle(deliveryService, "deliverOrder")
                .channel("customerChannel")
                .get();
    }


}
