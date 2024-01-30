package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServices implements UserDetailsService {

    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("Username or password incorrect")
        );

        return new CustomUserDetails(user.getUsername(),user.getPasswordHash(),authorities(user.getRole()));
    }

    public Collection<? extends GrantedAuthority> authorities(String role){
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }
}
