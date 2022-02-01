package com.checkskills.qcm.services;

import com.checkskills.qcm.model.*;
import com.checkskills.qcm.model.custom.QcmDetail;
import com.checkskills.qcm.model.custom.QcmLite;
import com.checkskills.qcm.model.custom.UserLite;
import com.checkskills.qcm.repository.*;
import com.checkskills.qcm.repository.custom.QcmLiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.checkskills.qcm.model.QcmHistoryStatus.COMPLETE;
import static com.checkskills.qcm.model.QcmHistoryStatus.UNUSED;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public ResponseEntity saveCandidateList(List<Candidate> candidateList, User user) {

        for (Candidate candidate : candidateList) {
            candidate.setUser(user);
            candidate.setAdded_date(new Date());
            candidateRepository.save(candidate);
        }

        return ResponseEntity.status(HttpStatus.OK).body(candidateList);
    }

    public ResponseEntity archiveCandidateList(List<Candidate> candidateList, User user) {

        for (Candidate candidate : candidateList) {
            Candidate dbCandidate = candidateRepository.findOneById(candidate.getId());
            dbCandidate.setArchived(true);
            candidateRepository.save(dbCandidate);
        }

        return ResponseEntity.status(HttpStatus.OK).body(candidateList);
    }

    public ResponseEntity updateCandidate(User user, Candidate candidate) {

        Candidate dbCandidate = candidateRepository.findOneById(candidate.getId());

        if(dbCandidate.getUser().getId() == user.getId()){
            dbCandidate.setLastname(candidate.getLastname());
            dbCandidate.setFirstname(candidate.getFirstname());
            dbCandidate.setEmail(candidate.getEmail());
            candidateRepository.save(dbCandidate);
        }

        return ResponseEntity.status(HttpStatus.OK).body(candidate);
    }

}
