package br.com.foody_delivery.order_tracking.infra.security;

import br.com.foody_delivery.order_tracking.domain.auth.repository.RevokedTokenRepository;
import br.com.foody_delivery.order_tracking.domain.auth.service.RevokedTokenService;
import br.com.foody_delivery.order_tracking.domain.auth.util.AuthTokenHashUtil;
import br.com.foody_delivery.order_tracking.domain.user.model.User;
import br.com.foody_delivery.order_tracking.domain.user.service.CustomUserDetailService;
import br.com.foody_delivery.order_tracking.dto.error.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;
    private final CustomUserDetailService customUserDetailService;
    private final RevokedTokenService revokedTokenService;

    public JwtFilter(
            JwtServiceImpl jwtService,
            CustomUserDetailService customUserDetailService,
            RevokedTokenService revokedTokenService
    ) {
        this.jwtService = jwtService;
        this.customUserDetailService = customUserDetailService;
        this.revokedTokenService = revokedTokenService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = jwtService.getRequestToken(request);

        if (token != null) {
            if (isRevokedToken(token, response)) {
                return;
            }
            String email = jwtService.verifyToken(token);
            User user = customUserDetailService.findByEmail(email);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isRevokedToken(String token, HttpServletResponse response) throws IOException {
        if (revokedTokenService.existsByTokenHash(AuthTokenHashUtil.sha256(token))) {
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            ErrorResponseDto body = new ErrorResponseDto(
                    "Não autorizado: Token inválido ou expirado",
                    HttpStatus.UNAUTHORIZED.value()
            );
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            return true;
        }
        return false;
    }

}
