package com.cbt.cbtapp.config;

import com.cbt.cbtapp.repository.UserRepository;
import com.cbt.cbtapp.security.AuthEntryPointJwt;
import com.cbt.cbtapp.security.JwtAuthenticationFilter;
import com.cbt.cbtapp.security.UserPrincipalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final UserRepository userRepository;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    public SecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers(AntPathRequestMatcher
//                                .antMatcher("/"),
//                        AntPathRequestMatcher
//                                .antMatcher("register"),
//                        AntPathRequestMatcher
//                                .antMatcher("/register/**"),
//                        AntPathRequestMatcher
//                                .antMatcher("/error"),
//                        AntPathRequestMatcher
//                                .antMatcher("/favicon.ico"),
//                        AntPathRequestMatcher
//                .antMatcher("/api/v1/auth/update_password"),
//                                       //.hasAuthority("CHANGE_PASSWORD_AUTHORITY"),
//                        AntPathRequestMatcher
//                                .antMatcher("/actuator/*"),
//                        AntPathRequestMatcher
//                                .antMatcher("/api/v1/auth/*")
//                )
//                .permitAll()
//                .anyRequest()
//                .permitAll()
//                .and()
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .sessionFixation().none()
//                .invalidSessionUrl("/login?invalid-session")
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(new JwtAuthenticationFilter(userDetailsService()), UsernamePasswordAuthenticationFilter.class)
//                .build();
//
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/","/api/v1/auth/","/","/api/v1/auth/","/api/v1/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**","/favicon.ico").permitAll()
                                .requestMatchers("/public/**").permitAll()
                                .requestMatchers("/api/v1/auth/update_password").hasAuthority("CHANGE_PASSWORD_AUTHORITY")
                                .requestMatchers("/api/test/**").permitAll()
                                .anyRequest().permitAll()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(new JwtAuthenticationFilter(userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserPrincipalService userDetailsService() {
        return new UserPrincipalService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }


    @Bean
    ApplicationListener<AuthenticationSuccessEvent> authSuccess() {
        return event -> {
            var auth = event.getAuthentication();
            log.info("LOGIN SUCCESSFUL [{}] - {}", auth.getClass().getSimpleName(), auth.getName());
        };
    }
}

