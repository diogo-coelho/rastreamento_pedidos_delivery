package br.com.foody_delivery.order_tracking.domain.user.service;

import br.com.foody_delivery.order_tracking.domain.user.model.User;
import br.com.foody_delivery.order_tracking.domain.user.repository.UserRepository;
import br.com.foody_delivery.order_tracking.exception.user.EmailNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServiceImpl implements CustomUserDetailService {

    private final UserRepository userRepository;

    public CustomUserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return findByEmail(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new EmailNotFoundException(email));
    }

}
