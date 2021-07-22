package com.peerlender.lendingengine.application.service.impl;

import com.peerlender.lendingengine.domain.model.User;

public interface TokenValidationService {
    User validateTokenAndGetUser(String token);
}
