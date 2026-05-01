package br.edu.uniacademia.ApostasBet.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtSecurityFilter jwtSecurityFilter;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager  authenticationManager(AuthenticationConfiguration auth){
      return auth.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**");
    }

    @Bean
    // Permissões na url
    public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c-> c.disable())
                .authorizeHttpRequests( a ->
                        a.requestMatchers("/api/public/**",
                                        "/api/auth/login").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/geral").hasRole("APOSTADOR")
                        .requestMatchers("/api/apostador/**")
                                .hasAnyRole("ADMIN","APOSTADOR")
                                .anyRequest().authenticated()
                )
                //.httpBasic(Customizer.withDefaults())
                .addFilterBefore( jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class )
                .sessionManagement(
                        s->
                                s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }

//    @Bean
//    // Usuários com permissão
//    public UserDetailsService userDetailsService() {
//        UserDetails u1 = User.withUsername("admin")
//                .password(getPasswordEncoder().encode("123"))
//                .roles("ADMIN").build();
//
//        UserDetails u2 = User.withUsername("apostador")
//                .password(getPasswordEncoder().encode("123"))
//                .roles("APOSTADOR").build();
//
//        return new InMemoryUserDetailsManager(u1, u2);
//
//    }


}
