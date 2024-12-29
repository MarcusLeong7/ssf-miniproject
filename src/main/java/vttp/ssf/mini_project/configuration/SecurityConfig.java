package vttp.ssf.mini_project.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${spring.security.user.name}")
    private String SPRING_SECURITY_USER_NAME;

    @Value("${spring.security.user.password}")
    private String SPRING_SECURITY_USER_PASSWORD;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // No authentication is required for these URLs
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/registered","/",
                                "/status",
                                "/home",
                                "/search",
                                "/recipes/{id}/information",
                                "/selected-meals",
                                "/mealplans",
                                "/mealplans/save-meal-plan",
                                "/mealplans/{mealPlanId}",
                                "/mealplans/{mealPlanId}/delete",
                                "/api",
                                "/api/meals",
                                "/api/mealplans/{mealPlanId}",
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
                );

        return http.build();
    }
}
