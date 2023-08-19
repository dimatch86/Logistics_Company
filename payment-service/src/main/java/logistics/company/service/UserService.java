package logistics.company.service;

import logistics.company.controller.UserNotFoundException;
import logistics.company.domain.User;
import logistics.company.domain.dto.BalanceDto;
import logistics.company.domain.dto.UserDto;
import logistics.company.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> addUser(UserDto userDto) {

        User newUser = new User(userDto.getName(), userDto.getBalance());
        User user = userRepository.save(newUser);
        log.info("New user № {} created", user.getId());
        return Optional.of(user);
    }

    public Boolean addMoney(int userId, BalanceDto balanceDto) {

        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
            user.setBalance(user.getBalance() + balanceDto.getAmount());
            userRepository.save(user);
            log.info("User № {} updated", user.getId());
            return true;
        } catch (RuntimeException e) {
            log.error(String.format("Update status failed: %s", e.getMessage()));
            return false;
        }
    }
}
