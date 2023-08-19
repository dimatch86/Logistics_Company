package logistics.company.domain.dto;

import logistics.company.domain.OrderStatus;
import lombok.Data;

@Data
public class StatusDto {
    private OrderStatus status;
}
