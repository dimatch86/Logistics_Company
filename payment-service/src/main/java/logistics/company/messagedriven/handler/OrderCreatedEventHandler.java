package logistics.company.messagedriven.handler;

import logistics.company.domain.Payment;
import logistics.company.domain.PaymentStatus;
import logistics.company.domain.User;
import logistics.company.messagedriven.OrderProcessorChannel;
import logistics.company.messagedriven.PaymentProcessorChannel;
import logistics.company.messagedriven.event.OrderEvent;
import logistics.company.messagedriven.event.PaymentEvent;
import logistics.company.repository.PaymentRepository;
import logistics.company.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@Component
@EnableBinding(OrderProcessorChannel.class)
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedEventHandler implements EventHandler<OrderEvent, PaymentEvent> {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;


    @StreamListener(target = OrderProcessorChannel.INPUT)
    @SendTo(PaymentProcessorChannel.INPUT)
    @Transactional
    @Override
    public PaymentEvent handleEvent(OrderEvent orderEvent) {

        double orderPrice = orderEvent.getCost();
        Integer userId = orderEvent.getUserId();
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .orderId(orderEvent.getId())
                .amount(orderEvent.getCost())
                .paymentStatus(PaymentStatus.DECLINED)
                .build();
        userRepository.findById(userId)
                .ifPresent(user -> deductUserBalance(orderPrice, paymentEvent, user));

        Payment payment = paymentEventToPayment(paymentEvent);
        paymentRepository.save(payment);
        log.info("Payment № {} created with status {}", payment.getId(), payment.getStatus());

        return paymentEvent;
    }

    private void deductUserBalance(double orderPrice, PaymentEvent paymentEvent, User user) {
        double userBalance = user.getBalance();
        if (userBalance >= orderPrice) {
            user.setBalance(userBalance - orderPrice);
            userRepository.save(user);
            paymentEvent.setPaymentStatus(PaymentStatus.APPROVED);
        }
    }

    private Payment paymentEventToPayment(PaymentEvent paymentEvent) {
        return new Payment(paymentEvent.getAmount(),
                paymentEvent.getOrderId(),
                LocalDateTime.now(),
                paymentEvent.getPaymentStatus());
    }
}