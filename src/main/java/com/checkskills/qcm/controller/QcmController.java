package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.Question;
import com.checkskills.qcm.model.custom.QcmLite;
import com.checkskills.qcm.repository.QcmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class QcmController {
    @Autowired
    private QcmRepository qcmRepository;

    @GetMapping("/qcm")
    @PreAuthorize("hasRole('EMPLOYER')")
    public List<QcmLite> getAllQcmLite(){
        return qcmRepository.findAllQcmLite();
    }

    @GetMapping("/qcm/{id}")
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('AUTHOR')")
    public Optional<Qcm> getQcmById(@PathVariable(value = "id") Long id) {
        return qcmRepository.findById(id);
    }

    @PostMapping("/qcm")
    @PreAuthorize("hasRole('AUTHOR')")
    public Qcm createQcm(@RequestBody Qcm qcm){
        return qcmRepository.save(qcm);
    }

    @PutMapping("/qcm/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public Qcm updateQcm(@RequestBody Qcm qcm, @PathVariable Long id){
        qcm.setId(id);
        return qcmRepository.save(qcm);
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
