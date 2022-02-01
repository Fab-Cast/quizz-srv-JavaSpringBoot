package com.checkskills.qcm.repository;

import com.checkskills.qcm.model.Answer;
import com.checkskills.qcm.model.Candidate;
import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.custom.QcmLite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByUserId(Long userId);
    Candidate findOneById(Long id);

}
