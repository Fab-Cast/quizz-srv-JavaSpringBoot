package com.checkskills.qcm.repository.custom;

import com.checkskills.qcm.model.Question;
import com.checkskills.qcm.model.custom.AnswerLite;
import com.checkskills.qcm.model.custom.QuestionLite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AnswerLiteRepository extends JpaRepository<AnswerLite, Long> {
    List<AnswerLite> findAllByQuestion(Question question);
}
