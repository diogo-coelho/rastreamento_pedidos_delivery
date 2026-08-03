package br.com.foody_delivery.order_tracking.domain.auth.service;

import br.com.foody_delivery.order_tracking.domain.auth.repository.RevokedTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class RevokedTokenServiceImpl implements RevokedTokenService {

    private final RevokedTokenRepository revokedTokenRepository;

    public RevokedTokenServiceImpl(RevokedTokenRepository revokedTokenRepository) {
        this.revokedTokenRepository = revokedTokenRepository;
    }

    @Override
    public boolean existsByTokenHash(String token) {
        return revokedTokenRepository.existsByTokenHash(token);
    }
}
