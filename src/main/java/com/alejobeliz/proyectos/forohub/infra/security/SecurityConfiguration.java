package com.alejobeliz.proyectos.forohub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * Configuración de seguridad para la aplicación.
 * Configura la autenticación, autorización y filtros de seguridad para las solicitudes HTTP.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private SecurityFilter securityFilter;

    /**
     * Constructor de la clase {@link SecurityConfiguration}.
     *
     * @param securityFilter El filtro de seguridad que maneja la validación del token JWT.
     */
    @Autowired
    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    /**
     * Configura el {@link SecurityFilterChain} para gestionar la seguridad HTTP.
     *
     * @param http La instancia de {@link HttpSecurity} para configurar la seguridad HTTP.
     * @return La instancia configurada de {@link SecurityFilterChain}.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST, "/login").permitAll() // Permitir solo POST en /login
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers("/swagger-ui.html","v3/api-docs/**","/swagger-ui/**").permitAll()//Para que swagger me pueda mostrar la documentacion sin problema
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Crea un {@link AuthenticationManager} para manejar la autenticación.
     *
     * @param configuration La configuración de autenticación.
     * @return La instancia de {@link AuthenticationManager}.
     * @throws Exception Si ocurre un error durante la creación del {@link AuthenticationManager}.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Crea un {@link PasswordEncoder} para encriptar contraseñas.
     *
     * @return La instancia de {@link PasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


