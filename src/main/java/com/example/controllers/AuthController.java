package com.example.controllers;

import com.example.models.AuthenticationRequest;
import com.example.models.AuthenticationResponse;
import com.example.models.UserModal;
import com.example.repositories.UserRepository;
import com.example.services.UserService;
import com.example.utils.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/subs")
    private ResponseEntity<?> subscribeClient(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        UserModal user = new UserModal();
        user.setUsername(username);
        user.setPassword(password);

        try {

            userRepository.save(user);
        } catch (Exception ex) {
            return ResponseEntity.ok(new AuthenticationResponse("Error saving client" + username));

        }
        return ResponseEntity.ok(new AuthenticationResponse("Successful subscription for client" + username));

    }

    @PostMapping("/auth")
    private ResponseEntity<?> authenticateClient(@RequestBody AuthenticationRequest authenticationRequest) {

        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception ex) {

            return ResponseEntity.ok(new AuthenticationResponse("Error authenticating client" + username));
        }

        UserDetails loadedUser = userService.loadUserByUsername(username);

        String generateToken = jwtUtils.generateToken(loadedUser);

        return ResponseEntity.ok(new AuthenticationResponse(generateToken));
    }
}
