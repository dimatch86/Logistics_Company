package logistics.company.service;


import logistics.company.domain.Order;
import logistics.company.domain.dto.OrderDto;
import logistics.company.domain.dto.StatusDto;

import java.util.Optional;

public interface OrderService {

    Optional<Order> addOrder(OrderDto orderDto);

    Boolean updateOrderStatus(Long id, StatusDto statusDto);
}
