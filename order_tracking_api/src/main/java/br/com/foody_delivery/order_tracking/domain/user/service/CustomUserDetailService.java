package br.com.foody_delivery.order_tracking.domain.user.service;

import br.com.foody_delivery.order_tracking.domain.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailService extends UserDetailsService {

    User findByEmail(String email);

}
