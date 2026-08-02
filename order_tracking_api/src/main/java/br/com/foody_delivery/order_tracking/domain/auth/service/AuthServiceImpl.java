package br.com.foody_delivery.order_tracking.domain.auth.service;

import br.com.foody_delivery.order_tracking.domain.user.model.User;
import br.com.foody_delivery.order_tracking.infra.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public String login(String email, String password) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        var authentication = authenticationManager.authenticate(authenticationToken);

        return jwtService.generateJwtToken((User) Objects.requireNonNull(authentication.getPrincipal()));
    }
}
