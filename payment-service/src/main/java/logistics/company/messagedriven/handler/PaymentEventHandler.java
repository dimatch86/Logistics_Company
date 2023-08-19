package logistics.company.messagedriven.handler;

import logistics.company.domain.PaymentStatus;
import logistics.company.domain.Transaction;
import logistics.company.domain.TransactionStatus;
import logistics.company.messagedriven.PaymentProcessorChannel;
import logistics.company.messagedriven.event.PaymentEvent;
import logistics.company.messagedriven.event.TransactionEvent;
import logistics.company.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@EnableBinding(PaymentProcessorChannel.class)
@Slf4j
@RequiredArgsConstructor
public class PaymentEventHandler implements EventHandler<PaymentEvent, TransactionEvent> {

    private final TransactionRepository transactionRepository;

    @Override
    @StreamListener(target = PaymentProcessorChannel.INPUT)
    @SendTo(PaymentProcessorChannel.OUTPUT)
    @Transactional
    public TransactionEvent handleEvent(PaymentEvent event) {

        TransactionEvent transactionEvent = TransactionEvent.builder()
                .orderId(event.getOrderId())
                .price(event.getAmount())
                .status(PaymentStatus.APPROVED.equals(event.getPaymentStatus()) ?
                        TransactionStatus.SUCCESSFUL :
                        TransactionStatus.UNSUCCESSFUL)
                .build();

        Transaction transaction = transactionEventToTransaction(transactionEvent);
        transactionRepository.save(transaction);
        log.info("Transaction № {} created with status {}", transaction.getId(), transaction.getStatus());
        return transactionEvent;
    }

    private Transaction transactionEventToTransaction(TransactionEvent transactionEvent) {
        return new Transaction(transactionEvent.getOrderId(),
                transactionEvent.getPrice(),
                LocalDateTime.now(),
                transactionEvent.getStatus());
    }
}