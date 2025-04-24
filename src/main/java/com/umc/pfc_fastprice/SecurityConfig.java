package com.umc.pfc_fastprice;

import com.umc.pfc_fastprice.service.AutenticarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AutenticarUsuarioService autenticarUsuarioService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth // Configura os endereços que podem ser acessados sem autenticação
                .requestMatchers("/", "/login", "/cadastrar", "/css/**", "/imgs/**").permitAll()
                .anyRequest().authenticated() // Os demais endereços requerem autenticação
            )
            .formLogin(form -> form // Configura o comportamento do formulário de login
                .loginPage("/login") // Intercepta o form definido no endereço "/login"
                .usernameParameter("email") // Define o campo de e-mail
                .passwordParameter("senha") // Define o campo de senha
                .defaultSuccessUrl("/", true) // Endereço redirecionada após o login
                .permitAll()
            )
            .logout(logout -> logout // Configura o comportameto de logout
                .logoutUrl("/logout") // Intercepta o redirecionamento para a página "/logout"
                .logoutSuccessUrl("/login?logout") // Endereço redirecionada após o login
            );
        return http.build();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Define uma instância do BCrypt para a confirmação de senha durante a autenticação
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(autenticarUsuarioService); // Define o serviço que irá configurar o método de autenticação do Spring Security
        auth.setPasswordEncoder(passwordEncoder()); // Define o encoder de senha para a autenticação
        return auth;
    }
}
