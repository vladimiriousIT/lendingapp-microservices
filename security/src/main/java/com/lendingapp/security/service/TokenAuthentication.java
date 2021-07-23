package com.lendingapp.security.service;

import com.google.common.collect.ImmutableMap;
import com.lendingapp.security.repository.UserRepository;
import com.lendingapp.security.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class TokenAuthentication implements UserAuthenticationService {
  private final TokenService tokenService;
  private final UserRepository userRepository;

  @Autowired
  public TokenAuthentication(TokenService tokenService, UserRepository userRepository) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
  }

  @Override
  public Optional<String> login(String username, String password) {
    return Optional.ofNullable(userRepository.findByUserDetails_Username(username))
      .filter(user -> user.get().getUserDetails().getPassword().equals(password))
      .map(user -> tokenService.expiring(ImmutableMap.of("username", username)));
  }

  @Override
  public User findByToken(String token) {
    Map<String, String> result = tokenService.verify(token);
    return userRepository.findByUserDetails_Username(result.get("username")).get();
  }
}
