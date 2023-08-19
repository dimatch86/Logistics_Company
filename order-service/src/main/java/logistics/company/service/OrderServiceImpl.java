package logistics.company.service;

import logistics.company.controller.OrderNotFoundException;
import logistics.company.domain.Order;
import logistics.company.domain.OrderStatus;
import logistics.company.domain.dto.OrderDto;
import logistics.company.domain.dto.StatusDto;
import logistics.company.messagedriven.processor.OrderProcessor;
import logistics.company.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final OrderProcessor orderProcessor;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProcessor orderProcessor) {
        this.orderRepository = orderRepository;
        this.orderProcessor = orderProcessor;
    }

    @Override
    public Optional<Order> addOrder(OrderDto orderDto) {

        LocalDateTime dateTime = LocalDateTime.now();
        Order newOrder = new Order(
                orderDto.getDepartureAddress(),
                orderDto.getDestinationAddress(),
                orderDto.getDescription(),
                orderDto.getCost(),
                orderDto.getUserId(),
                dateTime,
                dateTime,
                OrderStatus.AWAIT
        );
        Order order = orderRepository.save(newOrder);
        orderProcessor.process(order);
        logger.info("New order № {} created", order.getId());
        return Optional.of(order);
    }

    @Override
    public Boolean updateOrderStatus(Long id, StatusDto statusDto) {
        try {
            Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
            order.setStatus(statusDto.getStatus());
            orderRepository.save(order);
            logger.info("Order № {} updated", order.getId());
            return true;
        } catch (RuntimeException e) {
            logger.error(String.format("Update status failed: %s", e.getMessage()));
            return false;
        }
    }
}