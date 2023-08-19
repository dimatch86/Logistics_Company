package logistics.company.messagedriven.event;

import logistics.company.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderEvent implements Event {

    private Long id;
    private Double cost;
    private Integer userId;
    private LocalDateTime creationTime;
    private LocalDateTime modifiedTime;

    public static OrderEvent getOrderPurchaseEvent(Order order) {
        return new OrderEvent(order.getId(),
                order.getCost(),
                order.getUserId(),
                order.getCreationTime(),
                order.getModifiedTime());
    }

    @Override
    public String getEvent() {
        return "Event happened";
    }
}
