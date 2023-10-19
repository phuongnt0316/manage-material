package vn.com.devmaster.services.managematerial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin").hasAnyRole("ADMIN")
                .antMatchers("/ministore").hasAnyRole("USER","ADMIN");

//                .and().formLogin()
//                .loginPage("/login")
////                .usernameParameter("txtUserName")
////                .passwordParameter("txtPassword")
//                .loginProcessingUrl("/login-action")
 //               .permitAll();

        return  http.build();
    }


}
