package com.lendingapp.security.service;

import com.lendingapp.security.user.model.User;

import java.util.Optional;

public interface UserAuthenticationService {
  Optional<String> login(String username, String password);
  User findByToken(String token);
}
