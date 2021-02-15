package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.Answer;
import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.Question;
import com.checkskills.qcm.model.custom.AnswerLite;
import com.checkskills.qcm.model.custom.QuestionLauncher;
import com.checkskills.qcm.repository.AnswerRepository;
import com.checkskills.qcm.repository.QuestionRepository;
import com.checkskills.qcm.repository.custom.AnswerLiteRepository;
import com.checkskills.qcm.repository.custom.QuestionLauncherRepository;
import com.checkskills.qcm.services.QcmHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private AnswerLiteRepository answerLiteRepository;

    @Autowired
    private QuestionLauncherRepository questionLauncherRepository;

    @GetMapping("/question/{questionId}")
    public ResponseEntity getQuestionLauncherByQuestionId(@PathVariable(value = "questionId") Long questionId) {

        Optional<QuestionLauncher> question = questionLauncherRepository.findById(questionId);
        if(question.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(question);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune question trouvée en BDD");
        }

    }
}

