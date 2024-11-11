package br.com.nexus.goat.jwt;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        @Autowired
        private SecurityFilter securityFilter;

        private static final String[] PUBLIC_MATCHERS = {
                        "/",
                        "/swagger/**",
                        "/h2-console/**",
                        "/products/get-all"
        };

        private static final String[] PUBLIC_MATCHERS_POST = {
                        "/users/register",
                        "/users/login"
        };

        private static final String[] PRIVATE_MATCHERS_DELETE = {
                        "/features/delete/**",
                        "/categories/delete/**",
                        "/products/delete/**"
        };

        @Bean
        SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
                return httpSecurity
                                .cors(cors -> cors.configurationSource(request -> {
                                        CorsConfiguration configuration = new CorsConfiguration();
                                        configuration.setAllowedOrigins(Arrays.asList("*"));
                                        configuration.setAllowedMethods(
                                                        Arrays.asList("*"));
                                        configuration.setAllowedHeaders(Arrays.asList("*"));
                                        return configuration;
                                }))
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                                                .requestMatchers(PUBLIC_MATCHERS).permitAll()
                                                .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, PRIVATE_MATCHERS_DELETE)
                                                .hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

        @Bean
        AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
