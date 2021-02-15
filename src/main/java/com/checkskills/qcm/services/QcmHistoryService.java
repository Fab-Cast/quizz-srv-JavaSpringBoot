package com.checkskills.qcm.services;

import com.checkskills.qcm.model.*;
import com.checkskills.qcm.model.QcmHistoryStatus;
import com.checkskills.qcm.model.custom.QcmHistoryLite;
import com.checkskills.qcm.repository.QcmHistoryRepository;
import com.checkskills.qcm.repository.QcmRepository;
import com.checkskills.qcm.repository.custom.QcmHistoryLiteRepository;
import com.checkskills.qcm.repository.custom.QcmLiteRepository;
import com.checkskills.qcm.repository.custom.QuestionLiteRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QcmHistoryService {

    @Autowired
    private QcmHistoryRepository qcmHistoryRepository;

    @Autowired
    private QcmHistoryLiteRepository qcmHistoryLiteRepository;

    @Autowired
    private QcmLiteRepository qcmLiteRepository;

    @Autowired
    private QcmRepository qcmRepository;

    @Autowired
    private QuestionLiteRepository questionLiteRepository;


    private String createShortUniqueId(){
        String shortId = RandomStringUtils.randomAlphanumeric(9);
        QcmHistory qcmHistory = qcmHistoryRepository.findOneByCode(shortId);
        if(qcmHistory != null) {
            createShortUniqueId();
        }
        return shortId;
    }

    public ResponseEntity savePurchasedQcm(Qcm qcm, User user) {

        // String uniqueID = UUID.randomUUID().toString();

        String shortId = createShortUniqueId();

        QcmHistory qcmHistory = new QcmHistory();

        qcmHistory.setCode(shortId);
        qcmHistory.setQcm(qcm);
        qcmHistory.setEmployer(user);
        qcmHistory.setDateBought(new Date());
        qcmHistory.setStatus(QcmHistoryStatus.UNUSED);
        qcmHistory.setPurchased_credits(qcm.getCredits());

        return ResponseEntity.status(HttpStatus.OK).body(qcmHistoryRepository.save(qcmHistory));
    }

    public List<List<QcmHistoryLite>> getUserQcmHistory(List<Qcm> qcmList){

        List<List<QcmHistoryLite>> allQcmHistoryList = new ArrayList();
        for(Qcm qcm : qcmList) {
            List<QcmHistoryLite> qcmHistoryLiteListById = qcmHistoryLiteRepository.findAllByQcmId(qcm.getId());
            allQcmHistoryList.add(qcmHistoryLiteListById);
        }
        return allQcmHistoryList;
    }

    public ResponseEntity startRunningQcm(String code, String candidateName) {

        QcmHistory qcmHistory = qcmHistoryRepository.findOneByCode(code);
        if(qcmHistory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Code introuvable");
        }

        if(qcmHistory.getStatus() == QcmHistoryStatus.COMPLETE){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ce QCM a déjà été utilisé");
        }

        if(qcmHistory.getStatus() == QcmHistoryStatus.RUNNING){
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Ce QCM est en cours d'exécution");
        }

        Optional<Qcm> qcm = qcmRepository.findById(qcmHistory.getQcm().getId());

        List<Question> questionList = qcm.get().getQuestionList();

        List<Long> idList = new ArrayList<>();

        for (Question question : questionList) {
            idList.add(question.getId());
        }

        qcmHistory.setStatus(QcmHistoryStatus.RUNNING);
        qcmHistory.setDateUsed(new Date());
        qcmHistory.setCandidate_name(candidateName);
        qcmHistoryRepository.save(qcmHistory);

        return ResponseEntity.status(HttpStatus.OK).body(idList);

    }


}
