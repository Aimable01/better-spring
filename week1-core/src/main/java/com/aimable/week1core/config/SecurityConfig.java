package com.aimable.week1core.config;


import com.aimable.week1core.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // public endpoints
                        .requestMatchers("/auth/register", "/public/**").permitAll()

                        // role based authz
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/moderator/**").hasAnyRole("MODERATOR","ADMIN")
                        .requestMatchers("/api/users/**").hasAuthority("USER_READ")

                        // authority based authz
                        .requestMatchers("/api/posts/create").hasAuthority("POST_CREATE")
                        .requestMatchers("/api/posts/delete/**").hasAuthority("POST_DELETE")

                        // authenticated but no role required
                        .requestMatchers("/api/dashboard","/api/profile").authenticated()

                        .anyRequest().authenticated()
                )
//                .userDetailsService(customUserDetailsService)
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/auth/**", "/public/**","/api/**"))
//                .addFilterBefore(new DebugFilter(), BasicAuthenticationFilter.class)
                .build();
    }


    // when configuring in-memory users
     @Bean
     public UserDetailsService userDetailsService() {

     UserDetails adminUser = User.builder()
             .username("admin")
             .password(passwordEncoder().encode("admin@123"))
             .roles("ADMIN")
             .authorities("USER_READ","USER_WRITE","POST_READ","POST_WRITE","POST_DELETE")
             .build();

         UserDetails moderatorUser = User.builder()
                 .username("moderator")
                 .password(passwordEncoder().encode("moderator@123"))
                 .roles("MODERATOR")
                 .authorities("USER_READ", "POST_READ", "POST_WRITE")
                 .build();

         UserDetails regularUser = User.builder()
                 .username("user")
                 .password(passwordEncoder().encode("moderator@123"))
                 .roles("USER")
                 .authorities("POST_READ")
                 .build();

     return new InMemoryUserDetailsManager(adminUser, moderatorUser, regularUser);
     }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
