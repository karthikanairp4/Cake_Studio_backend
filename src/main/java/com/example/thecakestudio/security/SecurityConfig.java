package com.example.thecakestudio.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
//	    SecurityConfig tells Spring Security:
//		Which URLs are public?
//		Which URLs require login?
//		Are we using sessions or JWT?
//		Which authentication mechanism should be used?
//
//		It is the central configuration class for security.

	@Autowired
	private JwtFilter jwtFilter;

	@Autowired
	private OAuth2SuccessHandler oauth2SuccessHandler;

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        		.cors(Customizer.withDefaults())
                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // We are using JWT, so no HTTP Session
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )

                // Authorization Rules
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/getCakesByCategory/**",
                                "/findCakeById/**",
                                "/images/**",
                                "/oauth2/**",
                                "/login/**",
                                "/search/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )


                .oauth2Login(oauth ->
                oauth.successHandler(oauth2SuccessHandler)
        );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();

    }
}
