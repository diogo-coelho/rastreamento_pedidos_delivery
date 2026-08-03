package br.com.foody_delivery.order_tracking.infra.security;

import br.com.foody_delivery.order_tracking.domain.user.service.CustomUserDetailService;
import br.com.foody_delivery.order_tracking.dto.error.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;

    public SecurityConfiguration(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity
                .cors(cors ->
                    cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(
                        req -> {
                            req.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                            req.requestMatchers(
                                    "/v3/api-docs/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/user/register",
                                    "/auth/login").permitAll();
                            req.anyRequest().authenticated();
                        })
                .exceptionHandling(this::exceptionHandler)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            CustomUserDetailService customUserDetailService,
            PasswordEncoder passwordEncoder
    ) {
        var authenticationProvider = new DaoAuthenticationProvider(customUserDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    private void exceptionHandler(ExceptionHandlingConfigurer<HttpSecurity> ex) {
        ex.accessDeniedHandler((request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            ErrorResponseDto body = new ErrorResponseDto(
                    "Acesso negado: " + accessDeniedException.getMessage(),
                    HttpStatus.FORBIDDEN.value()
            );
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        });

        ex.authenticationEntryPoint((request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            ErrorResponseDto body = new ErrorResponseDto(
                    "Não autorizado: " + authException.getMessage(),
                    HttpStatus.UNAUTHORIZED.value()
            );
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        });
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
