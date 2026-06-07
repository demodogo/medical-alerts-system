package cl.duoc.dsy2206.medicalalerts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
  return http.build();
  /*
   * http .authorizeHttpRequests(authorize -> authorize .anyRequest().permitAll() ) .csrf(csrf ->
   * csrf.disable());
   * 
   * return http.build();
   */
 }
}
