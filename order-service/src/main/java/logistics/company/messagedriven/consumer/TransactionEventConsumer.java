package logistics.company.messagedriven.consumer;

import logistics.company.domain.OrderStatus;
import logistics.company.domain.TransactionStatus;
import logistics.company.messagedriven.event.TransactionEvent;
import logistics.company.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableBinding(Sink.class)
public class TransactionEventConsumer implements EventConsumer<TransactionEvent> {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    @StreamListener(target = Sink.INPUT)
    public void consumeEvent(TransactionEvent event) {

        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
                    order.setStatus(TransactionStatus.SUCCESSFUL.equals(event.getStatus()) ?
                            OrderStatus.COMPLETED :
                            OrderStatus.FAILED);
                    orderRepository.save(order);
                    log.info("Order № {} updated with status {}", order.getId(), order.getStatus());
                });
    }
}