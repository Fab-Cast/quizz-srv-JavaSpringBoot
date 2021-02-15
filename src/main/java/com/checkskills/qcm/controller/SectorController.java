package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.Plan;
import com.checkskills.qcm.model.Sector;
import com.checkskills.qcm.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SectorController {

    @Autowired
    private SectorRepository sectorRepository;

    @GetMapping("/sector")
    public ResponseEntity getAllSector(){
        List<Sector> sectorList = sectorRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sectorList);
    }
}
