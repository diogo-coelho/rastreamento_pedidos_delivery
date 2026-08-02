package br.com.foody_delivery.order_tracking.domain.user.service;

import br.com.foody_delivery.order_tracking.domain.user.model.User;
import br.com.foody_delivery.order_tracking.domain.user.repository.UserRepository;
import br.com.foody_delivery.order_tracking.dto.user.UserRequestDto;
import br.com.foody_delivery.order_tracking.exception.user.EmailAlreadyExistsException;
import br.com.foody_delivery.order_tracking.exception.user.EmailNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(UserRequestDto data) {
        if (userRepository.existsByEmail(data.email())) {
            throw new EmailAlreadyExistsException(data.email());
        }

        var encryptedPassword = passwordEncoder.encode(data.password());
        var user = new User(data.name(), data.email(), encryptedPassword);
        return userRepository.save(user);
    }

}
