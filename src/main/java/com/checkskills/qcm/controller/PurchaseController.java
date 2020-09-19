package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.QcmHistory;
import com.checkskills.qcm.model.Subscription;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.repository.UserRepository;
import com.checkskills.qcm.services.PurchaseService;
import com.checkskills.qcm.services.QcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/purchase/{qcmId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity purchase(@PathVariable(value = "qcmId") Long qcmId, Authentication authentication){

        User user = userRepository.findByUsername(authentication.getName());
        ResponseEntity response = purchaseService.purchaseQcm(qcmId, user);

        return response;

    }





}
