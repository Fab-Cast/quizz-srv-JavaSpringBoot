package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.Candidate;
import com.checkskills.qcm.model.Mail;
import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.model.custom.*;
import com.checkskills.qcm.repository.CandidateRepository;
import com.checkskills.qcm.repository.QcmHistoryRepository;
import com.checkskills.qcm.repository.QcmRepository;
import com.checkskills.qcm.repository.UserRepository;
import com.checkskills.qcm.repository.custom.QcmLiteRepository;
import com.checkskills.qcm.services.CandidateService;
import com.checkskills.qcm.services.QcmHistoryService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CandidateController {

    private static final Logger LOGGER= LoggerFactory.getLogger(CandidateController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/candidate")
    @PreAuthorize("hasRole('EMPLOYER')")
        public ResponseEntity getAllCandidate(Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        List<Candidate> candidateList = candidateRepository.findByUserId(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(candidateList);
    }

    @PostMapping("/candidate")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity createCandidateList(@RequestBody List<Candidate> candidateList, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        return candidateService.saveCandidateList(candidateList, user);
    }

    @PostMapping("/candidate/archive")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity archiveCandidateList(@RequestBody List<Candidate> candidateList, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        return candidateService.archiveCandidateList(candidateList, user);
    }

    @PutMapping("/candidate")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity updateCandidate(@RequestBody Candidate candidate, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        return candidateService.updateCandidate(user, candidate);
    }

}
