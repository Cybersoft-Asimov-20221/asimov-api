package com.cybersoft.asimovapi.teachers.middleware;

import com.cybersoft.asimovapi.security.middleware.JwtAuthenticationEntryPointTeacher;
import com.cybersoft.asimovapi.teachers.domain.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration("TeacherSecurityConfig")
@Order(1000)
public class WebSecurityConfigTeacher extends WebSecurityConfigurerAdapter {
    @Autowired
    TeacherService teacherService;

    @Autowired
    JwtAuthenticationEntryPointTeacher unauthorizedHandler;

    @Bean
    public JwtAuthorizationFilterTeacher authorizationFilterTeacher() {
        return new JwtAuthorizationFilterTeacher();
    }


    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(teacherService);
    }

    /*@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/v1/teachers/auth/**", "/swagger-ui/*", "/api-docs/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authorizationFilterTeacher(), UsernamePasswordAuthenticationFilter.class);

    }

}
