package com.velo.microserviceusermanagement.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        this.logger.debug("Using default configure(HttpSecurity). "
                + "If subclassed this will potentially override subclass configure(HttpSecurity).");

        // Cross Origin Resource Sharing: because we are working with different ports,
        http.cors();

        // starts authorizing configurations.
        http.authorizeRequests()
                // ignore guest's URLs.
                .antMatchers("/resources/**", "/error", "/service/**").permitAll()
                // authenticate remaining URLs.
                .anyRequest().fullyAuthenticated()
                .and()
                .logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/service/logout", "POST"));

        // login form
        http.formLogin().loginPage("service/login");

        // enable basic header authentication
        http.httpBasic()
                .and()
                // cross-side request forgery
                .csrf().disable();
    }


    // Define our customized UserDetailService.
    // Specify how user details kept in application (database, LDAP or memory
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    // this class is a configuration class. So we define new bean or we can customize other spring configurations.
    // Allow all origins for TESTING!
    @Bean  // beans a re SINGLETON by default
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                WebMvcConfigurer.super.addCorsMappings(registry);
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    // define password encoder
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
