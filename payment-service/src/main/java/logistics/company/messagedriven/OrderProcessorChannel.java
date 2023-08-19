package logistics.company.messagedriven;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderProcessorChannel {

    String INPUT = "orderEventProcessor-in-0";

    @Input(INPUT)
    SubscribableChannel myInput();

    @Output("orderEventProcessor-out-0")
    MessageChannel anOutput();
}
