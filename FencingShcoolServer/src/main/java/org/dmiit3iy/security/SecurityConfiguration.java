package org.dmiit3iy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/FencingSchool/user").permitAll()
                .antMatchers(HttpMethod.POST, "/FencingSchool/user/").permitAll()
                .antMatchers(HttpMethod.PATCH, "/FencingSchool/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/FencingSchool/user/").permitAll()
                .antMatchers(HttpMethod.POST,"/FencingSchool/trainer/").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/FencingSchool/trainer/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/FencingSchool/trainer/**").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
