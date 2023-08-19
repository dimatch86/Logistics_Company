package logistics.company.messagedriven.event;

import logistics.company.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEvent implements Event {

    private Long id;
    private Long orderId;
    private Double amount;
    private LocalDateTime creationTime;
    private PaymentStatus paymentStatus;

    @Override
    public String getEvent() {
        return "Event happened";
    }
}
