package com.learn.springboot.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SpringDataUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public SpringDataUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username);
        if(user == null){
            return null;
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),
                Stream.of(user.getRoles()).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );
    }
}
