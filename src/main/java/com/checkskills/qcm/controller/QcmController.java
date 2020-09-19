package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.*;
import com.checkskills.qcm.model.custom.QcmLite;
import com.checkskills.qcm.repository.QcmRepository;
import com.checkskills.qcm.repository.UserRepository;
import com.checkskills.qcm.services.QcmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QcmController {

    private static final Logger LOGGER= LoggerFactory.getLogger(QcmController.class);

    @Autowired
    private QcmService qcmService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QcmRepository qcmRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/qcmLite")
    public ResponseEntity getAllQcmLite(){
        List<QcmLite> qcmLiteList = qcmService.findAllQcmLite();
        return ResponseEntity.status(HttpStatus.OK).body(qcmLiteList);
    }

    @GetMapping("/qcm")
    public ResponseEntity getAllQcm(){
        List<Qcm> qcmList = qcmService.findAllQcm();
        return ResponseEntity.status(HttpStatus.OK).body(qcmList);
    }

    @GetMapping("/qcm/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('AUTHOR')")
    public ResponseEntity getQcmById(@PathVariable(value = "id") Long id, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        Qcm qcm = qcmService.getQcmById(id,user);
        return ResponseEntity.status(HttpStatus.OK).body(qcm);
    }

    @GetMapping("/qcm/current-auth")
    @PreAuthorize("hasRole('USER') or hasRole('AUTHOR')")
    public ResponseEntity getCurrentAuthQcmList(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        List<Qcm> qcmList = qcmRepository.findAllByUser(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(qcmList);
    }

    @PostMapping("/qcm")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity saveQcm(@RequestBody Qcm qcm, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        Qcm qcmSaved = qcmService.saveQcm(user, qcm);
        return ResponseEntity.status(HttpStatus.OK).body(qcmSaved);
    }

    @PostMapping("/qcm/complete/{code}")
    public ResponseEntity verifyQcm(@RequestBody Qcm qcm, @PathVariable(value = "code") String code){
        return ResponseEntity.status(HttpStatus.OK).body( qcmService.verifyQcm(qcm, code));
    }

    @PutMapping("/qcm/{id}")
    @PreAuthorize("hasRole('AUTHOR') and #qcm.user.username == authentication.principal.username") // vérifie que "utilisateur" dans le qcm envoyé correstonde à celui qui est connecté
    public ResponseEntity updateQcm(@RequestBody Qcm qcm, @PathVariable Long id, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        ResponseEntity response = qcmService.updateQcm(user, id, qcm);
        return response;
    }

    @DeleteMapping(value = "/qcm/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity deleteQcm(@PathVariable Long id, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        ResponseEntity response = qcmService.deleteQcm(user, id);
        return response;
    }

}
