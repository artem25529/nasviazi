package com.es.nasviazi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String USER_BY_USER_NAME_QUERY = "SELECT username, password, enabled FROM users WHERE username = ?";
    private static final String AUTHORITIES_BY_USER_NAME_QUERY = "SELECT u.username, a.authority FROM users u LEFT JOIN user_authority ua ON " +
            "u.username = ua.user_id LEFT JOIN authorities a ON a.id = ua.authority_id WHERE u.username = ?";

    @Autowired
    private DataSource dataSource;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers("/", "/login", "/script.js","/style.css", "/reg").permitAll();
                    authConfig.requestMatchers("/admin").hasAuthority("ADMIN");
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/login");
                    login.defaultSuccessUrl("/");
                })
                .logout(logout -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                    logout.logoutSuccessUrl("/");
                    logout.deleteCookies("JSESSIONID");
                    logout.invalidateHttpSession(true);
                });
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsManager() {
        final JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(USER_BY_USER_NAME_QUERY);
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(AUTHORITIES_BY_USER_NAME_QUERY);
        return jdbcUserDetailsManager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
