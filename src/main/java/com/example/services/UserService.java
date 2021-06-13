package com.example.services;

import java.util.ArrayList;

import com.example.models.UserModal;
import com.example.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModal existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            return null;
        }
        String name = existingUser.getUsername();
        String pwd = existingUser.getPassword();

        return new User(name, pwd, new ArrayList<>());

    }

}
