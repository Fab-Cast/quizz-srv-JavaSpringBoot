package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.*;
import com.checkskills.qcm.model.custom.QcmHistoryAverage;
import com.checkskills.qcm.model.custom.QcmHistoryLite;
import com.checkskills.qcm.model.custom.QcmHistoryOrder;
import com.checkskills.qcm.model.custom.QcmLite;
import com.checkskills.qcm.repository.QcmHistoryRepository;
import com.checkskills.qcm.repository.QcmRepository;
import com.checkskills.qcm.repository.UserRepository;
import com.checkskills.qcm.repository.custom.QcmLiteRepository;
import com.checkskills.qcm.services.QcmHistoryService;
import com.checkskills.qcm.services.QcmService;
import org.hibernate.mapping.Any;
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
public class QcmController {

    private static final Logger LOGGER= LoggerFactory.getLogger(QcmController.class);

    @Autowired
    private QcmService qcmService;

    @Autowired
    private QcmHistoryService qcmHistoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QcmRepository qcmRepository;

    @Autowired
    private QcmLiteRepository qcmLiteRepository;

    @Autowired
    private QcmHistoryRepository qcmHistoryRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/qcmLite")
    public ResponseEntity getAllVisibleQcmLite(){
        List<QcmLite> qcmLiteList = qcmService.findAllVisibleQcmLite();
        return ResponseEntity.status(HttpStatus.OK).body(qcmLiteList);
    }

    @GetMapping("/qcmLite/{id}")
    public ResponseEntity getQcmLiteById(@PathVariable(value = "id") Long id) {
        Optional<QcmLite> qcmLite = qcmService.findQcmLite(id);
        return ResponseEntity.status(HttpStatus.OK).body(qcmLite);
    }

    @GetMapping("/qcm")
    public ResponseEntity getAllQcm(){
        List<Qcm> qcmList = qcmService.findAllQcm();
        return ResponseEntity.status(HttpStatus.OK).body(qcmList);
    }

    @GetMapping("/qcm/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity getQcmById(@PathVariable(value = "id") Long id, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        Qcm qcm = qcmService.getQcmById(id,user);
        return ResponseEntity.status(HttpStatus.OK).body(qcm);
    }

    /*
    @GetMapping("/qcm/code/{code}")
    public ResponseEntity getQcmByCode(@PathVariable(value = "code") String code) {
        //return qcmHistoryService.getQcmToRun(code);
        return qcmHistoryService.getIdList(code);
    }
    */


    @PostMapping("/qcm/code/{code}")
    public ResponseEntity getQcmByCode(@PathVariable(value = "code") String code, @RequestBody String candidateName){
        return qcmHistoryService.startRunningQcm(code, candidateName);
    }

    @GetMapping("/qcm/get-author-qcm-list")
    @PreAuthorize("hasRole('USER') or hasRole('AUTHOR')")
    public ResponseEntity getCurrentAuthQcmList(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());

        Map<String, Object> result = new HashMap<String,Object>();
        List<Qcm> qcmList = qcmRepository.findAllByUser(user.getId());
        List<List<QcmHistoryLite>> allQcmHistorylist = qcmHistoryService.getUserQcmHistory(qcmList);

        result.put("qcmList",qcmList);
        result.put("qcmHistoryArrayList",allQcmHistorylist);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/qcmLite/ordered")
    public ResponseEntity getOrderedQcmList(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        List<QcmHistoryOrder> qcmLiteList = qcmHistoryRepository.findQcmHistoryOrder(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(qcmLiteList);
    }

    @GetMapping("/qcm/qcmHistoryAverage/{qcmId}")
    public ResponseEntity getQcmHistoryAverage(@PathVariable Long qcmId) {
        List<QcmHistoryAverage> qcmHistoryAverage = qcmHistoryService.getAverage(qcmId);
        return ResponseEntity.status(HttpStatus.OK).body(qcmHistoryAverage);
    }

    @PostMapping("/qcm")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity saveQcm(@RequestBody Qcm qcm, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        Qcm qcmSaved = qcmService.saveQcm(user, qcm);
        return ResponseEntity.status(HttpStatus.OK).body(qcmSaved);
    }

    @PostMapping("/qcm/complete/")
    public ResponseEntity verifyQcm(@RequestBody Map<String, Object> qcmCandidate){
        return qcmService.saveCandidateQcmHistory(qcmCandidate);
    }

    @PutMapping("/qcm/{id}")
    @PreAuthorize("hasRole('AUTHOR') and #qcm.user.username == authentication.principal.username") // vérifie que "utilisateur" dans le qcm envoyé correstonde à celui qui est connecté
    public ResponseEntity updateQcm(@RequestBody Qcm qcm, @PathVariable Long id, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        ResponseEntity response = qcmService.updateQcm(user, id, qcm);
        return response;
    }

    @PutMapping("/qcm/cancel-qcm")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity cancelQcm(@RequestBody Map<String, Object> customQcm){
        return ResponseEntity.status(HttpStatus.OK).body( qcmService.cancelQcm(customQcm));
    }

    @DeleteMapping(value = "/qcm/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity deleteQcm(@PathVariable Long id, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        ResponseEntity response = qcmService.deleteQcm(user, id);
        return response;
    }

}
