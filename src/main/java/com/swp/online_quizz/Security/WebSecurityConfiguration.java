package com.swp.online_quizz.Security;


import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.CustomUserDetails;
import com.swp.online_quizz.Service.CustomUserDetailsServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final CustomUserDetailsServices customUserDetailsServices;
    private final Filter filter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> {
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/homePageTeacher/**").hasRole("TEACHER");
                    auth.requestMatchers("/", "/register", "/forgotpassword", "/verifyaccount","/regenerateotp","/setpassword", "/Css/**", "/images/**", "/Font/**", "/fonts/**", "/Js/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(login -> login
                        .loginPage("/login").permitAll()
                        .failureUrl("/login?unsuccessful")
                        .successHandler(myAuthenticationSuccessHandler())
                )
                .exceptionHandling(a->a.accessDeniedPage("/login?nopermit"))
                .oauth2Login(a->a.loginPage("/login").successHandler(myAuthenticationSuccessHandler()))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler();
    }

    @Component
    public static class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Autowired
        private UsersRepository usersRepository;
        @Autowired
        private CustomUserDetailsServices customUserDetailsServices;
        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            context.setAuthentication(authentication);
            if (authentication.getPrincipal() instanceof DefaultOidcUser) {
                DefaultOidcUser userDetails = (DefaultOidcUser) authentication.getPrincipal();
                String username = userDetails.getAttribute("email");
                String user = usersRepository.findEmailByEmailIgnoreCase(username);
                if (user==null) {
                    //Nếu k tồn tại
                    String redirectUrl = "/register";
                    String firstname = userDetails.getGivenName();
                    String lastname = userDetails.getFamilyName();
                    request.getSession().setAttribute("email",username);
                    request.getSession().setAttribute("firstnameEmail",firstname);
                    request.getSession().setAttribute("lastnameEmail",lastname);
                    SecurityContextHolder.clearContext();
                    redirectStrategy.sendRedirect(request,response,redirectUrl);
                }
                if(user!=null && authentication.getPrincipal() instanceof DefaultOidcUser){

                    DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();

                    //Lấy email từ DefaultOidcUser
                    String email = oidcUser.getEmail();
                    User user1 = usersRepository.findByEmailIgnoreCase(email).get();
                    UserDetails userDetail = customUserDetailsServices.loadUserByUsername(user1.getUsername());

                    SecurityContextHolder.clearContext();

                    authentication = new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            if (authentication.getPrincipal() instanceof CustomUserDetails){
                request.getSession().setAttribute("authentication", authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = usersRepository.findByUsername(customUserDetails.getUsername()).get();
            if(!user.getIsActive()){
                request.getSession().invalidate();
                SecurityContextHolder.clearContext();
                if(user.getRole().equals("ROLE_TEACHER")){
                    request.getSession().setAttribute("ms","This account have not been activated! Contacting ADMIN to be granted access");
                    getRedirectStrategy().sendRedirect(request, response, "/login");
                }
                else if(user.getRole().equals("ROLE_STUDENT")){
                    request.getSession().setAttribute("err","This account have not been verified! Click here to ");
                    request.getSession().setAttribute("email",user.getEmail());
                    getRedirectStrategy().sendRedirect(request, response, "/login");
                }

            }else{
                Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();

                String role = "";
                if (!authorities.isEmpty()) {
                    role = authorities.iterator().next().getAuthority().trim();
                }
                if (role.contains("ROLE_ADMIN")) {
                    // Nếu người dùng có vai trò ROLE_ADMIN, chuyển hướng đến URL "/admin"
                    getRedirectStrategy().sendRedirect(request, response, "/admin");
                } else {
                    super.onAuthenticationSuccess(request, response, authentication);
                }
            }

        }
        }
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsServices).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}