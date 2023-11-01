package ru.otus;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import ru.otus.service.StoreService;

@SpringBootApplication
public class SpringHomework15Application {


    public static void main(String[] args) {

        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(SpringHomework15Application.class);

        StoreService service = ctx.getBean(StoreService.class);
        service.makeOrders();

    }




}
