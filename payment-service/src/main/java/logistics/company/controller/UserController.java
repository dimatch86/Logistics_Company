package logistics.company.controller;

import logistics.company.domain.User;
import logistics.company.domain.dto.BalanceDto;
import logistics.company.domain.dto.UserDto;
import logistics.company.repository.UserRepository;
import logistics.company.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/user")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public User listUser(@PathVariable Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody UserDto input) {
        Optional<User> result = userService.addUser(input);
        return result.isPresent() ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @PatchMapping("/user/{userId}")
    public ResponseEntity<?> refillUserBalance(@PathVariable int userId, @RequestBody BalanceDto balanceDto) {
        Boolean result = userService.addMoney(userId, balanceDto);
        return result ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
}
