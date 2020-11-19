package org.library.com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aditya
 * Configuration/Implementation for
 * Security mechanism for  this API
 */
@Configuration
// enballing Web Security
@EnableWebSecurity
public class SecureConfiguration extends WebSecurityConfigurerAdapter {


    /*
     * Manually set Password and username for API Authenticarion
     *
     * @Bean
     * @Override protected UserDetailsService userDetailsService()
     * {
     * List<UserDetails> users=new ArrayList<>();
     * users.add(User.withDefaultPasswordEncoder().username("adi").password("123").roles("USER").build());
     * return new InMemoryUserDetailsManager(users);
     * }
     * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
     * auth.inMemoryAuthentication().withUser("adi").password("123").roles("USER");
     * }
     */

    /**
     * autowire AuthenticationEntryPoint class
     */
    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    /**
     * Overriden configure() of WebSecurityConfigurerAdapter class
     * for configuration of http header
     *
     * @param http HttpSecurity object
     * @throws Exception throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
//        For Fixing Error Page Showing when Using H2 DAtabase
        http.headers().frameOptions().sameOrigin();
    }

    /**
     * BCrypt password encoding  enabling
     *
     * @return BCryptPasswordEncoder object
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Used for manual set of own user name and
     * BCrypted generate password
     * of own password.
     * Giving the security role
     * as USER.
     *
     * @param auth AuthenticationManagerBuilder object
     * @throws Exception throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("adi").password("$2a$04$53m0UQT5xVegOVOfB.3Wkuv/0YDaVu/6B3DWzWI1m.BXZVhzthqgi").roles("USER");
    }
}
