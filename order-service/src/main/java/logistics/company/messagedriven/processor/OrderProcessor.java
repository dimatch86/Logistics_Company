package logistics.company.messagedriven.processor;

import logistics.company.domain.Order;
import logistics.company.messagedriven.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableBinding(Source.class)
public class OrderProcessor {

    @Autowired
    private Source source;

    public void process(Order order) {

        Message<OrderEvent> message =
                MessageBuilder.withPayload(OrderEvent.getOrderPurchaseEvent(order)).build();
        source.output().send(message);
        log.info("SAGA started");
    }
}