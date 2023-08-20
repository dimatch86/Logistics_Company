package logistics.company.controller;

import logistics.company.domain.Order;
import logistics.company.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    OrderRepository orderRepository;

    @RequestMapping("/api/order/page")
    public String index(Model model) {

        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("ordersCount", orders.size());
        return "index";
    }
}
