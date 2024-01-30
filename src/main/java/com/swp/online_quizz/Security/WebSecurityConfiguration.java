package com.swp.online_quizz.Security;


import com.swp.online_quizz.Service.CustomUserDetailsServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class WebSecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final DataSource dataSource;
    private final CustomUserDetailsServices customUserDetailsServices;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/", "/Css/**", "/images/**", "/Font/**", "/fonts/**", "/Js/**").permitAll()
                                .requestMatchers("/admin/**").hasAuthority("Admin")
                                .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?unsuccessful")
                        .successHandler(myAuthenticationSuccessHandler())
                ).logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/login?logout"))
                        .logoutSuccessUrl("/")
                        .logoutSuccessHandler(myLogoutSuccessHandler())
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                        response.sendRedirect("/login")
                                )
                );
        ;
        httpSecurity.userDetailsService(customUserDetailsServices).passwordManagement(pass -> passwordEncoder());
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler myLogoutSuccessHandler() {
        return new MyLogoutSuccessHandler();
    }

    public static class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            super.onAuthenticationSuccess(request, response, authentication);
            // Lưu UserDetails vào SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    public static class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            super.onLogoutSuccess(request, response, authentication);
            // Xóa UserDetails khỏi SecurityContextHolder khi đăng xuất
            SecurityContextHolder.clearContext();
        }
    }


}
