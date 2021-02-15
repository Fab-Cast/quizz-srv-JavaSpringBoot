package com.checkskills.qcm.repository.custom;

import com.checkskills.qcm.model.Answer;
import com.checkskills.qcm.model.custom.QcmLite;
import com.checkskills.qcm.model.custom.QuestionLite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionLiteRepository extends JpaRepository<QuestionLite, Long> {

    List<QuestionLite> findByQcmId(Long qcmId);

}
