package com.checkskills.qcm.services;

import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.QcmHistory;
import com.checkskills.qcm.model.QcmHistoryStatus;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.repository.QcmHistoryRepository;
import com.checkskills.qcm.repository.QcmRepository;
import com.checkskills.qcm.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QcmHistoryService {

    @Autowired
    private QcmHistoryRepository qcmHistoryRepository;

    public ResponseEntity saveQcmHistory(User user, Qcm qcm, int totalWrong) {

        QcmHistory qcmHistory = new QcmHistory();

        qcmHistory.setUser(user);
        qcmHistory.setQcm(qcm);
        qcmHistory.setDateUsed(new Date());
        qcmHistory.setStatus(QcmHistoryStatus.COMPLETE);

        int note = 10 - (totalWrong * 10 / qcm.getQuestionList().size());
        qcmHistory.setNote(note);

        return ResponseEntity.status(HttpStatus.OK).body(qcmHistoryRepository.save(qcmHistory));
    }
}
