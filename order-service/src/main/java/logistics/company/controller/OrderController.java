package logistics.company.controller;

import logistics.company.domain.Order;
import logistics.company.domain.dto.OrderDto;
import logistics.company.domain.dto.StatusDto;
import logistics.company.repository.OrderRepository;
import logistics.company.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GetMapping("/api/order")
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/api/order/{orderId}")
    public Order listOrder(@PathVariable Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @PostMapping("/api/order")
    public ResponseEntity<?> addOrder(@RequestBody OrderDto input) {
        Optional<Order> result = orderService.addOrder(input);
        return result.isPresent() ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @PatchMapping("/api/order/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody StatusDto statusDto) {
        Boolean result = orderService.updateOrderStatus(orderId, statusDto);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
