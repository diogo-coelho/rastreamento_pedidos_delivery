package br.com.foody_delivery.order_tracking.infra.security;

import br.com.foody_delivery.order_tracking.domain.user.model.User;
import br.com.foody_delivery.order_tracking.domain.user.service.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailService customUserDetailService;

    public JwtFilter(JwtService jwtService, CustomUserDetailService customUserDetailService) {
        this.jwtService = jwtService;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getRequestToken(request);

        if (token != null) {
            String email = jwtService.verifyToken(token);
            User user = customUserDetailService.findByEmail(email);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getRequestToken(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

}
