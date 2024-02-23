package com.swp.online_quizz.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class Filter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContextHolder.clearContext();
        try {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");

            if(authentication == null){
                response.sendRedirect("/login");
                return;
            }

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
//            String email = (String) session.getAttribute("email");
//            if(email!=null){
//                request.getSession().setAttribute("email",email);
//            }
            request.getSession().setAttribute("authentication",authentication);
            filterChain.doFilter(request,response);
        }catch (Exception e){
            response.sendRedirect("/login");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        return requestURI.equals("/") ||
                requestURI.equals("/register") ||
                requestURI.equals("/login") ||
                requestURI.equals("/forgotpassword")||
                requestURI.equals("/forgot-password")||
                requestURI.equals("/set-password") ||
                requestURI.startsWith("/Css/") ||
                requestURI.startsWith("/images/") ||
                requestURI.startsWith("/Font/") ||
                requestURI.startsWith("/fonts/") ||
                requestURI.startsWith("/Js/") ||
                requestURI.startsWith("/Information");
    }
}
