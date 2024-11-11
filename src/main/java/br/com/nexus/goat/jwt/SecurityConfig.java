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
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        @Autowired
        private SecurityFilter securityFilter;

        private static final String[] PUBLIC_MATCHERS = {
                        "/**"
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
                                .cors(cors -> cors.disable())
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

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
                configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE",
                                "OPTIONS", "PATH"));
                final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/", configuration);
                return source;
        }

}
