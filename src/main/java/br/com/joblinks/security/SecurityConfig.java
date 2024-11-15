package br.com.joblinks.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsManager = new InMemoryUserDetailsManager();

        var user = User.withUsername("admin")
                .password("{noop}senha123")  // "yourpassword" deve ser sua senha
                .roles("USER")
                .build();

        userDetailsManager.createUser(user);
        System.out.println(user);
        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desabilita a proteção CSRF
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/usuarios/login", "/api/usuarios/cadastro").permitAll()  // Permite acesso a /cadastro
                                .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());  // Configuração básica de autenticação

        return http.build();
    }

}

