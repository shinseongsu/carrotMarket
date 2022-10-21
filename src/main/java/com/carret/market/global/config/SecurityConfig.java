package com.carret.market.global.config;

import com.carret.market.support.AuthenticationEntryPointImpl;
import com.carret.market.support.handler.LoginFailHandler;
import com.carret.market.support.handler.LoginSuccessHandler;
import com.carret.market.application.auth.AuthUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthUserDetailsService authUserDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // url 보안
        http.authorizeRequests()
            .antMatchers("/", "/error", "/images/**", "/script/**", "/img/**", "/login", "/h2-console/**", "/register", "/myGeolocation", "/item/list").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated();

        http.csrf().disable();

        // X-Frame-Options header 이슈
        http.headers()
            .frameOptions()
            .disable();

        // 로그인 관련
        http.formLogin()
            .loginPage("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .successHandler(loginSuccessHandler)
            .failureHandler(loginFailHandler)
            .permitAll();

        // 자동 로그인
        http.rememberMe()
            .rememberMeParameter("remember")
            .tokenValiditySeconds(3600)
            .alwaysRemember(true)
            .userDetailsService(authUserDetailsService);

        // 로그아웃
        http.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true);

        // 세션관련
        http.sessionManagement()
            .maximumSessions(1)
            .maxSessionsPreventsLogin(true);

        http.exceptionHandling()
            .authenticationEntryPoint(new AuthenticationEntryPointImpl());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailsService)
            .passwordEncoder(bCryptPasswordEncoder());
    }

}
