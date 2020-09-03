package com.checkskills.qcm.services;

import com.checkskills.qcm.model.*;
import com.checkskills.qcm.model.custom.QcmLite;
import com.checkskills.qcm.repository.QcmHistoryRepository;
import com.checkskills.qcm.repository.QcmRepository;
import com.checkskills.qcm.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QcmService {

    @Autowired
    private QcmRepository qcmRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QcmHistoryRepository qcmHistoryRepository;

    @Autowired
    private QcmHistoryService qcmHistoryService;

    public Qcm saveQcm(User user, Qcm qcm) {
        qcm.setUser(user);
        return qcmRepository.save(qcm);
    }

    public List<QcmLite> findAllQcmLite() {
        return qcmRepository.findAllQcmLite();
    }

    public Qcm getQcmById(Long id, User user) {

        // TODO: Envoyer le qcm seulement quand un code acheteur est délivré

        Optional<Qcm> optionalQcm = qcmRepository.findById(id);
        if (optionalQcm.isPresent()){
            Qcm qcm = optionalQcm.get();
            Set<Role> userAuthRoles = user.getRoles(); // vérifier les roles e l'utilisateur authentifié
            if(userAuthRoles.stream().filter(o -> o.getName().toString().equals("ROLE_USER")).findFirst().isPresent()){ // si l'utilisateur a "ROLE_USER"
                qcm.getQuestionList().forEach(q -> q.getAnswerList().forEach(a -> a.setCorrect(false))); // on met les réponses à faux
                return qcm;

            }else{
                return qcm;
            }
        }
        return null;
    }

    public ResponseEntity deleteQcm(User user, Long id) {

        Long authUserId = user.getId();
        Long qcmDbUserId = qcmRepository.findById(id).get().getUser().getId();

        if(authUserId != qcmDbUserId){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le propriétaire de ce QCM est différent de l'utilisateur authentifié");
        }

        try {
            qcmRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("QCM Supprimé");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("QCM introuvable");
        }
    }

    public ResponseEntity updateQcm(User user, Long id, Qcm qcm) {

        // TODO: Si le qcm reçu est différent du qcm en base de données : on indente la colonne "version" de QCM.

        Optional<Qcm> qcmInDb = qcmRepository.findById(id);

        if(qcmInDb.isPresent()) { // on vérifie que le qcm existe dans la db

            Long authUserId = user.getId();
            Long qcmDbUserId = qcmRepository.findById(id).get().getUser().getId();

            if(authUserId != qcmDbUserId){ // si user identifié dans le qcm de la bdd != user authentifié
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur propriétaire de ce QCM est différent de l'utilisateur authentifié");
            }

            try {
                qcm.setId(id);
                qcmRepository.save(qcm);
                return ResponseEntity.status(HttpStatus.OK).body(qcm);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Problème à l'enregistrement du QCM");
            }

        }

        // Id qcm pas trouvé en db
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("l'id de ce QCM n'a pas été trouvé en base de données");

    }

    public ResponseEntity verifyQcm(User user, Qcm qcm, Long qcmId) {

        int totalWrong = 0;

        Optional<Qcm> optionalQcm = qcmRepository.findById(qcmId);
        if (optionalQcm.isPresent()){
            Qcm dbQcm = optionalQcm.get();
            List<Answer> answerList = new ArrayList<Answer>();

            dbQcm.getQuestionList().forEach(q ->
                    q.getAnswerList().forEach(a ->{
                                answerList.add(a);
                            }
                    )
            );

            for (Question question : qcm.getQuestionList()) {
                boolean wrongAnswer = false;
                for (Answer answer : question.getAnswerList()) {
                    for (Answer goodAnswer : answerList){
                        if(answer.getId().equals(goodAnswer.getId())){
                            if(answer.getCorrect() != goodAnswer.getCorrect()){
                                wrongAnswer = true;
                            }
                        }
                    }
                }
                Question dbQuestion = questionRepository.getOne(question.getId());
                if(wrongAnswer == true){
                    totalWrong = totalWrong +1 ;
                    int totalFalse = dbQuestion.getTotalFalse();
                    dbQuestion.setTotalFalse(totalFalse + 1 );
                }else{
                    int totalTrue = dbQuestion.getTotalTrue();
                    dbQuestion.setTotalTrue(totalTrue + 1);
                }
                questionRepository.save(dbQuestion);
            }

            return ResponseEntity.status(HttpStatus.OK).body(qcmHistoryService.saveQcmHistory(user, dbQcm, totalWrong));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("QCM pas trouvé");

    }

}
