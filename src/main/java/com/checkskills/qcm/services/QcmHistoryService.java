package com.checkskills.qcm.services;

import com.checkskills.qcm.model.*;
import com.checkskills.qcm.model.QcmHistoryStatus;
import com.checkskills.qcm.model.custom.CodeCandidateAssociation;
import com.checkskills.qcm.model.custom.QcmHistoryAverage;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

import static com.checkskills.qcm.model.QcmHistoryStatus.COMPLETE;

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

    public ResponseEntity startRunningQcm(String code, String candidateMail) {

        QcmHistory qcmHistory = qcmHistoryRepository.findOneByCode(code);

        if(qcmHistory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Code introuvable");
        }

        if(!qcmHistory.getCandidate_mail().equals(candidateMail)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ce code a été attribué à un autre candidat");
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
        //qcmHistory.setCandidate_name(candidateName);
        qcmHistoryRepository.save(qcmHistory);

        return ResponseEntity.status(HttpStatus.OK).body(idList);

    }

    public List<QcmHistoryAverage> getAverage(Long qcmId){

        List<QcmHistory> qcmHistoryList = qcmHistoryRepository.findAllByQcmId(qcmId);

        // todo : refaire findAllByQcmId pour récupérer seulement les 'complete' et renvoyer le résultat que si il y en a assez (10)
        /*
        if(qcmHistoryList.size() < 15){
            return new ArrayList<>();
        }
         */

        List<QcmHistoryAverage> allQcmHistoryList = new ArrayList();

        Integer percentValues[] = {10,20,30,40,50,60,70,80,90,100};

        List<String> qcmHistorySuccess = new ArrayList<String>();

        for(QcmHistory qcmHistory : qcmHistoryList) {

            if(qcmHistory.getStatus() == COMPLETE){
                Long roundedSuccess = null;
                // J'arrondis à la dizaine au dessus pour éviter d'avoir des valeurs à 0
                // Ex : une note à 2% est affiché dans le groupe "10%
                if(qcmHistory.getSuccess() != 100 && qcmHistory.getSuccess() >= Math.round(qcmHistory.getSuccess()/10.0) * 10){
                    roundedSuccess = Math.round((qcmHistory.getSuccess()+10)/10.0) * 10;
                }else{
                    roundedSuccess = Math.round(qcmHistory.getSuccess()/10.0) * 10;
                }

                qcmHistorySuccess.add(roundedSuccess.toString());
            }

        }

        for(Integer percentValue : percentValues) {

            //int valueLength = Collections.frequency(Arrays.asList(qcmHistorySuccess), percentValue);
            int valueLength = Collections.frequency(qcmHistorySuccess, percentValue.toString());;

            QcmHistoryAverage average = new QcmHistoryAverage();
            average.setPercentage(valueLength*100/qcmHistoryList.size());
            average.setSuccessNote(percentValue);

            allQcmHistoryList.add(average);
        }

        return allQcmHistoryList;
    }


    public ResponseEntity setCodeCandidateNameAssociation(CodeCandidateAssociation codeAssociation){

        QcmHistory qcmHistory = qcmHistoryRepository.findOneByCode(codeAssociation.getCode());
        qcmHistory.setCandidate_mail(codeAssociation.getCandidate_mail());
        qcmHistory.setCandidate_name(codeAssociation.getCandidate_name());
        qcmHistory.setStatus(QcmHistoryStatus.INVITED);
        qcmHistory.setDateInvited(new Date());
        qcmHistoryRepository.save(qcmHistory);
        return ResponseEntity.status(HttpStatus.OK).body(codeAssociation);
    }

    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmail(Mail mail) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg);

        helper.setTo(mail.getEmail());
        helper.setSubject(mail.getObject());
        helper.setText(mail.getBody(), true);

        javaMailSender.send(msg);

    }


    public ResponseEntity sendMailList(List<Mail> mailList){


        for(Mail mail : mailList) {

            try {
                CodeCandidateAssociation codeAssociation = new CodeCandidateAssociation();
                codeAssociation.setCandidate_mail(mail.getEmail());
                codeAssociation.setCandidate_name(mail.getCandidate_name());
                codeAssociation.setCode(mail.getCode());
                setCodeCandidateNameAssociation(codeAssociation);
                sendEmail(mail);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(mailList);
    }

    public ResponseEntity removeCodeCandidateAssociation(String code){
        QcmHistory qcmHistory = qcmHistoryRepository.findOneByCode(code);
        qcmHistory.setCandidate_name(null);
        qcmHistory.setCandidate_mail(null);
        qcmHistory.setStatus(QcmHistoryStatus.UNUSED);
        qcmHistory.setDateInvited(null);
        qcmHistoryRepository.save(qcmHistory);
        return ResponseEntity.status(HttpStatus.OK).body(qcmHistory);
    }


}
