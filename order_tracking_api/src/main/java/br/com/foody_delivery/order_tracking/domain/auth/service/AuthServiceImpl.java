package br.com.foody_delivery.order_tracking.domain.auth.service;

import br.com.foody_delivery.order_tracking.domain.auth.model.RevokedToken;
import br.com.foody_delivery.order_tracking.domain.auth.repository.RevokedTokenRepository;
import br.com.foody_delivery.order_tracking.domain.auth.util.AuthTokenHashUtil;
import br.com.foody_delivery.order_tracking.domain.user.model.User;
import br.com.foody_delivery.order_tracking.infra.security.JwtServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;
    private final RevokedTokenRepository revokedTokenRepository;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            JwtServiceImpl jwtService,
            RevokedTokenRepository revokedTokenRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.revokedTokenRepository = revokedTokenRepository;
    }

    @Override
    public String login(String email, String password) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        var authentication = authenticationManager.authenticate(authenticationToken);

        return jwtService.generateJwtToken((User) Objects.requireNonNull(authentication.getPrincipal()));
    }

    @Override
    @Transactional
    public void logout() {
        var request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        var token = jwtService.getRequestToken(request);

        var expirationDate = jwtService.getExpirationDateFromToken(token);
        String hash = AuthTokenHashUtil.sha256(token);

        revokedTokenRepository.save(new RevokedToken(hash, expirationDate));

        SecurityContextHolder.clearContext();
    }
}
