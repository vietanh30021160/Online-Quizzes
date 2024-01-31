package com.swp.online_quizz.Security;


import com.swp.online_quizz.Service.CustomUserDetailsServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final CustomUserDetailsServices customUserDetailsServices;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers( "/","/register","/forgotpassword","/Css/**", "/images/**", "/Font/**", "/fonts/**", "/Js/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login").permitAll()
                        .failureUrl("/login?unsuccessful")
                        .successHandler(myAuthenticationSuccessHandler())
                );
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .accessDeniedHandler((request, response, accessDeniedException) ->
//                                response.sendRedirect("/login"))
//                );
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
//            var authorities = authentication.getAuthorities();
//            var roles = authorities.stream().map(r->r.getAuthority()).findFirst();
//            if(roles.orElse("").equals("ROLE_ADMIN")){
//                response.sendRedirect("/admin");
//            }else{
//                response.sendRedirect("/");
//            }
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

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailsServices).passwordEncoder(passwordEncoder());
    }


}
