package com.peerLending.securityapp.application;

import com.peerLending.securityapp.service.NotificationService;
import com.peerLending.securityapp.user.dto.UserDTO;
import com.peerLending.securityapp.user.exception.UserNotFoundException;
import com.peerLending.securityapp.user.model.User;
import com.peerLending.securityapp.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Autowired
    public UserController(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @PostMapping("/register")
    public void register(@RequestBody UserDTO userDTO){
        User user = new User(userDTO.getUsername(),userDTO.getPassword());
        userRepository.save(user);
        notificationService.sentMessage(userDTO);
    }

    @PostMapping("/validate")
    public String validateTokenAndGuestUsername(@RequestHeader("Authorization") String token){
        return userRepository.findById(token).orElseThrow(()-> new UserNotFoundException()).getUsername();
    }
}
