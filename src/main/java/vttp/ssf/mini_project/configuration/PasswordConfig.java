package vttp.ssf.mini_project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/registered","/",
                                "/process-login",
                                "/search",
                                "/recipes/{id}/information",
                                "/selected-meals",
                                "/images/**",
                                "/css/**",
                                "/js/**",
                                "/favicon.ico" ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // custom login page
                        .loginProcessingUrl("/auth-login") // Your custom login endpoint
                        .defaultSuccessUrl("/home", true)// Redirect after successful login
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/user/login?logout")
                        .permitAll()
                );
        return http.build();
    }
}
