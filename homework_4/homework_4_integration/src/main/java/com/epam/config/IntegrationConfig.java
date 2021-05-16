package com.epam.config;

import com.epam.entity.Order;
import com.epam.entity.OrderStatus;
import com.epam.service.Storage;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import java.util.List;

@Log4j
@Configuration
@EnableIntegration
@ComponentScan("com.epam")
@IntegrationComponentScan
public class IntegrationConfig {


    public static final String file = "src/main/resources/file.csv";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(IntegrationConfig.class);
        ctx.refresh();

        DirectChannel out1 = ctx.getBean("outputChannel1", DirectChannel.class);
        out1.subscribe(x -> log.info(x + " outputChannel1"));

        DirectChannel out2 = ctx.getBean("outputChannel2", DirectChannel.class);
        out2.subscribe(x -> log.info(x + " outputChannel2"));

        List<Order> orders = ctx.getBean(IntegrationConfig.MGateway.class).parse(IntegrationConfig.file);

        log.info(orders + " orders");

        for (Order order : orders) {
            ctx.getBean(MGateway.class).addOrder(order);
        }

        log.info(Storage.storage + " global storage");

    }

    @Bean
    DirectChannel outputChannel1() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel outputChannel2() {
        return new DirectChannel();
    }


    @MessagingGateway
    public interface MGateway {

        @Gateway(requestChannel = "outputChannel1")
        List<Order> parse(String file);

        @Gateway(requestChannel = "addOrderFlow.input")
        void addOrder(Order order);
    }

    @Bean
    public IntegrationFlow addOrderFlow() {
        return flow -> flow
                .filter(notCanceledOrders())
                .handle("storage", "addOrder")
                .channel("outputChannel2");
    }

    @Bean
    public IntegrationFlow parser() {
        return IntegrationFlows.from("outputChannel1")
                .handle("orderParseService", "parse")
                .get();
    }

    @Bean
    public GenericSelector<Order> notCanceledOrders() {
        return order -> !order.getOrderStatus().equals(OrderStatus.CANCELED);
    }

}
