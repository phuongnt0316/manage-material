package vn.com.devmaster.services.managematerial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        ((HttpSecurity) ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl) ((HttpSecurity)
                        http.csrf().disable()).authorizeHttpRequests()
                        .antMatchers("/admin"))
                        .hasAuthority("ADMIN")
                        .antMatchers(new String[]{"/product-manage"}))
                .hasAnyAuthority("ADMIN", "USER")
                .antMatchers(new String[]{"/**"}))
                .permitAll().and()).formLogin()
                .loginPage("/login").permitAll();
        return (SecurityFilterChain) http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public HttpFirewall defaultHttpFirewall() {
//        return new DefaultHttpFirewall();
//    }
}
