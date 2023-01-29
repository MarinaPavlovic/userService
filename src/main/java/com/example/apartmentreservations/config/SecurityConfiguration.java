package com.example.apartmentreservations.config;

import com.example.apartmentreservations.filters.JwtRequestFilter;
import com.example.apartmentreservations.services.UserService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private JwtRequestFilter jwtRequestFilter;

    public SecurityConfiguration(UserService userService, JwtRequestFilter jwtRequestFilter) {
        this.userService = userService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure (HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/auth/login").permitAll().antMatchers("/auth/registration").permitAll().antMatchers("/reservations/apartments/id").permitAll().
                anyRequest().authenticated().and().
                exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    public void configure (AuthenticationManagerBuilder builder) throws Exception{

            builder.userDetailsService(userService);
    }



    }





