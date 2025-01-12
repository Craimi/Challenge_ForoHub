package com.ambystudio.forohub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable) //Desabilita las cookies
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Aqui indicamos que la aplicacion es stateless
                .authorizeHttpRequests((authorizeHttpRequest) -> authorizeHttpRequest
                        // Endpoints públicos
                        .requestMatchers(HttpMethod.POST, "/login").permitAll() //Permite que cualquiera acceda al endpoint sin necesidad de autenticarse
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                        // Endpoints globales
                        .requestMatchers(HttpMethod.PUT, "/**").hasRole("MODERADOR")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("MODERADOR")

                        // Curso endpoints
                        .requestMatchers(HttpMethod.GET, "/cursos", "/cursos/{id}").authenticated() // GET accesible a todos los usuarios autenticados
                        .requestMatchers(HttpMethod.POST, "/cursos").hasRole("MODERADOR") // POST solo para moderadores

                        // Topico endpoints
                        .requestMatchers(HttpMethod.GET, "/topicos", "/topicos/{id}").authenticated() // GET accesible a todos los usuarios autenticados
                        .requestMatchers(HttpMethod.POST, "/topicos").authenticated() // POST accesible a todos los usuarios autenticados

                        // Usuario endpoints
                        .requestMatchers(HttpMethod.GET, "/usuarios", "/usuarios/{id}").hasRole("MODERADOR") // GET solo para moderadores
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll() // POST accesible a todos


                        .anyRequest().authenticated()) //Cualquier solicitud no especificada arriba requiere autenticacion
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
