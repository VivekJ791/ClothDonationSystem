package com.example.clothdonationsystem.service.impl;

import com.example.clothdonationsystem.model.User;
import com.example.clothdonationsystem.repo.UserRepository;
import com.example.clothdonationsystem.service.impl.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" User name not found for " + username));

        return UserDetailImpl.build(user);
    }
}
