package logistics.company.messagedriven.event;

import logistics.company.domain.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEvent implements Event {

    private Long orderId;

    private double price;

    private TransactionStatus status;
    @Override
    public String getEvent() {
        return "Event happened";
    }
}
