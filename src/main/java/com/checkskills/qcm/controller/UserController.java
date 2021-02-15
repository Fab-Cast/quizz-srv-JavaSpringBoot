package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/current-auth/")
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('AUTHOR') or hasRole('ADMIN')")
    public ResponseEntity getCurrentAuthUser(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/user/picture")
    public ResponseEntity saveQcm(@RequestBody byte[] picByte, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        user.setPicture(picByte);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
