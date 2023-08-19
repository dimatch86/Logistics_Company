package logistics.company.messagedriven;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentProcessorChannel {

    String INPUT = "paymentEventSubscriber-in-0";
    String OUTPUT = "paymentEventSubscriber-out-0";

    @Input(INPUT)
    SubscribableChannel myInput();

    @Output(OUTPUT)
    MessageChannel anOutput();
}
