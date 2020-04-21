package com.checkskills.qcm.repository;

import com.checkskills.qcm.model.Answer;
import com.checkskills.qcm.model.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository {
    List<Answer> findByQuestionId(Long questionId);
}
