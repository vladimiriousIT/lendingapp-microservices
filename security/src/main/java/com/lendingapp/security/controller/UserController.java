package com.lendingapp.security.controller;

import com.lendingapp.security.repository.UserRepository;
import com.lendingapp.security.service.UserAuthenticationService;
import com.lendingapp.security.user.model.User;
import com.lendingapp.security.user.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UserRepository userRepository;
  private final UserAuthenticationService userAuthenticationService;

  @Autowired
  public UserController(UserRepository userRepository, UserAuthenticationService userAuthenticationService) {
    this.userRepository = userRepository;
    this.userAuthenticationService = userAuthenticationService;
  }

  @PostMapping("/register")
  public String register(@RequestBody UserDetailsImpl userDetails){
    userRepository.save(new User(userDetails));
    return login(userDetails);
  }

  @PostMapping("/login")
  public String login(@RequestBody UserDetailsImpl userDetails){
    return userAuthenticationService
      .login(userDetails.getUsername(), userDetails.getPassword())
      .orElseThrow(()-> new RuntimeException("Invalid Login Details!"));
  }
}
