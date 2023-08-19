package logistics.company;

import logistics.company.domain.User;
import logistics.company.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CmdRunner implements CommandLineRunner {

    private static final String USER_NAME = "Test User";
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {

        User user = userRepository.findUserByName(USER_NAME);
        if (user == null) {
            userRepository.save(new User(USER_NAME, 255.54));
        }
    }
}
