package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.model.custom.QcmLite;
import com.checkskills.qcm.repository.QcmRepository;
import com.checkskills.qcm.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Optional;



@RestController
public class QcmController {

    private static final Logger LOGGER= LoggerFactory.getLogger(QcmController.class);

    @Autowired
    private QcmRepository qcmRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/qcm")
    //@PreAuthorize("hasRole('EMPLOYER')")
    public List<QcmLite> getAllQcmLite(){
        return qcmRepository.findAllQcmLite();
    }

    @GetMapping("/qcm/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('AUTHOR')")
    public Optional<Qcm> getQcmById(@PathVariable(value = "id") Long id) {
        return qcmRepository.findById(id);
    }

    @PostMapping("/qcm")
    @PreAuthorize("hasRole('AUTHOR')")
    public Qcm createQcm(@RequestBody Qcm qcm, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        qcm.setUser(user);
        return qcmRepository.save(qcm);
    }

    @PutMapping("/qcm/{id}")
    //@PreAuthorize("hasRole('AUTHOR')")
    @PreAuthorize("hasRole('AUTHOR') and #qcm.user.username == authentication.principal.username") // vérifie que "utilisateur" dans le qcm envoyé correstonde à celui qui est connecté
    public Qcm updateQcm(@RequestBody Qcm qcm, @PathVariable Long id, Authentication authentication){

        // Todo

        User authUser = userRepository.findByUsername(authentication.getName());

        Optional<Qcm> qcmInDb = qcmRepository.findById(id);
        if(qcmInDb.isPresent()) {
            Qcm existingQcm = qcmInDb.get();
            User userInQcmInDb = existingQcm.getUser();
            Long userIdInQcmInDb = userInQcmInDb.getId();

            LOGGER.info("- Ok user present et on a son id  ----------------------------------------------------------");

            if(userIdInQcmInDb == authUser.getId()){
                LOGGER.info("-Ok save----------------------------------------------------------");
                qcm.setId(id);
                return qcmRepository.save(qcm);
            }else{
                LOGGER.info("-pas le même utilisateur ! ----------------------------------------------------------");
            }
        } else {
            LOGGER.info("-Id qcm pas trouvé en db----------------------------------------------------------");
        }

        return null;


    }

    @DeleteMapping(value = "/qcm/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<Void> deleteQcm(@PathVariable Long id) {
        try {
            qcmRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
