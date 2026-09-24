package ch.realflorianchrist.caddms.user;

import java.util.List;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;

    @QueryMapping
    public UserEntity user(@Argument("userId") UUID userId) {
        return userRepository.findById(userId)
                .orElse(null);
    }

    @QueryMapping
    public List<UserEntity> users() {
        return userRepository.findAll();
    }
}
