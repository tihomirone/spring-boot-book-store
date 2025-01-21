package com.mj.book_seller.security;

import com.mj.book_seller.model.Role;
import com.mj.book_seller.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfigs {

  private final CustomUserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  public SecurityConfigs(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Value("${authentication.internal-api-key}")
  private String internalApiKey;

  @Bean
  public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);

    return authenticationManagerBuilder.build();
  }

  /*@Bean
  @Order(1)
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults());
    http.csrf(csrfConfigurer -> csrfConfigurer.disable());
    http.sessionManagement(
        sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.securityMatcher("/api/authentication/**")
        .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());

    return http.build();
  }

  @Bean
  public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

    return http.build();
  }*/

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults());
    http.csrf(csrfConfigurer -> csrfConfigurer.disable());
    http.sessionManagement(
        sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/api/authentication/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/book").permitAll()
        .requestMatchers("/api/book/**").hasRole(Role.ADMIN.name())
        .requestMatchers("/api/internal/**").hasRole(Role.SYSTEM_MANAGER.name())
        .anyRequest().authenticated());

    http
        .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(internalApiAuthenticationFilter(), JwtAuthorizationFilter.class);

    return http.build();
  }

  public InternalApiAuthenticationFilter internalApiAuthenticationFilter() {
    return new InternalApiAuthenticationFilter(internalApiKey);
  }

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter() {
    return new JwtAuthorizationFilter();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*");
      }
    };
  }
}
