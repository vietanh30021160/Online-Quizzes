package com.swp.online_quizz.Security;

import com.swp.online_quizz.Service.CustomUserDetails;
import com.swp.online_quizz.Service.CustomUserDetailsServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserPasswordFilter extends OncePerRequestFilter {

    private UserDetails userDetails;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy thông tin người dùng hiện tại từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (authentication != null && authentication.isAuthenticated() && authentication instanceof UsernamePasswordAuthenticationToken) {
            // Lấy thông tin người dùng hiện tại từ Authentication
            UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();

            // Lấy thông tin người dùng đang lưu (chẳng hạn từ cơ sở dữ liệu hoặc nơi khác)
            UserDetails savedUserDetails = userDetails;

            // So sánh thông tin người dùng hiện tại và người dùng đang lưu
            if (!currentUserDetails.equals(savedUserDetails)) {
                // Thực hiện các hành động khi thông tin người dùng thay đổi (ví dụ: logout)
                SecurityContextHolder.clearContext();
            }
        }

        // Tiếp tục xử lý chuỗi lọc
        filterChain.doFilter(request, response);
    }
}
