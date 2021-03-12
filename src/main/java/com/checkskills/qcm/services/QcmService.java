package com.checkskills.qcm.services;

import com.checkskills.qcm.model.*;
import com.checkskills.qcm.model.custom.QcmLite;
import com.checkskills.qcm.repository.QcmHistoryRepository;
import com.checkskills.qcm.repository.QcmRepository;
import com.checkskills.qcm.repository.QuestionRepository;
import com.checkskills.qcm.repository.custom.QcmLiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;

import static com.checkskills.qcm.model.QcmHistoryStatus.COMPLETE;
import static com.checkskills.qcm.model.QcmHistoryStatus.UNUSED;

@Service
public class QcmService {

    @Autowired
    private QcmRepository qcmRepository;

    @Autowired
    private QcmLiteRepository qcmLiteRepository;

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

    public List<QcmLite> findAllVisibleQcmLite() {
        return qcmLiteRepository.findByVisible(true);
    }

    public Optional<QcmLite> findQcmLite(Long id) {return qcmLiteRepository.findById(id); }

    public List<Qcm> findAllQcm() {
        return qcmRepository.findAll();
    }

    public Qcm getQcmById(Long id, User user) {

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
            return ResponseEntity.status(HttpStatus.OK).body(qcmDbUserId);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("QCM introuvable");
        }
    }

    public ResponseEntity updateQcm(User user, Long id, Qcm qcm) {

        Optional<Qcm> qcmInDb = qcmRepository.findById(id);

        if(qcmInDb.isPresent()) {

            try {
                qcm.setId(id);
                qcm.setNote(SetQcmNote(qcm));
                qcmRepository.save(qcm);
                return ResponseEntity.status(HttpStatus.OK).body(qcm);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Problème à l'enregistrement du QCM");
            }
        }

        // Id qcm pas trouvé en db
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("l'id de ce QCM n'a pas été trouvé en base de données");

    }


    public ResponseEntity cancelQcm(Map<String, Object> customQcm) {

        Long qcmId = Long.valueOf(customQcm.get("id").toString());

        Optional<QcmHistory> qcmHistoryInDb = qcmHistoryRepository.findById(qcmId);

        if(qcmHistoryInDb.isPresent()) {
            if(qcmHistoryInDb.get().getStatus() == COMPLETE){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le passage du qcm est terminé");
            }
            qcmHistoryInDb.get().setStatus(UNUSED);
            qcmHistoryInDb.get().setCandidate_name(null);
            qcmHistoryInDb.get().setDateUsed(null);
            qcmHistoryRepository.save(qcmHistoryInDb.get());
            return ResponseEntity.status(HttpStatus.OK).body("Le qcm a bien été réinitialisé");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("l'id de ce QCM n'a pas été trouvé en base de données");

    }


    public Float SetQcmNote(Qcm qcm){

        Float totalAllQNote = Float.valueOf(0);
        int totalUnknown = 0;
        for (Question question : qcm.getQuestionList()) {
            int qNote = 5;
            // Si on a pas assez de réponses, on met une note de "2" à la question
            if(question.getTotalRight() + question.getTotalWrong() < 10){
                qNote = 2;
                totalUnknown ++;
            }else{

                // S'il y a trop de réponses fausses
                if(question.getTotalRight() / question.getTotalWrong() <= 1.5){
                    qNote = qNote -3;
                }

                // S'il y a trop de réponses justes (question trop facile)
                if(question.getTotalRight() / question.getTotalWrong() > 4){
                    qNote = qNote -3;
                }

                // Si l'utilisateur n'a pas répondu assez vite (total_no_time)
                if(question.getTotalNoTime() != 0 && (question.getTotalRight() + question.getTotalWrong()) / question.getTotalNoTime() < 6){
                    qNote = qNote -2;
                }

                // Si l'utilisateur a trop souvent utilisé un joker sur la question
                if(question.getTotalJoker() != 0 && (question.getTotalRight() + question.getTotalWrong()) / question.getTotalJoker() < 6){
                    qNote = qNote -2;
                }


                // Si la note est négative, on la bloque à 0
                if(qNote<0){
                    qNote = 0;
                }

            }

            totalAllQNote = totalAllQNote + qNote;

        }

        // si plus de 10% des questions n'ont pas eu assez de réponses pour les comptabiliser, on attribue pas de note
        if((totalUnknown * 100 / qcm.getQuestionList().size())>10){
            return null;
        }

        // calcul de la note moyenne du Qcm
        Float QcmGlobalNote = totalAllQNote / qcm.getQuestionList().size();

        // Si il y a beaucoup de questions dans le QCM, on ajoute 1 point à la note moyenne du Qcm
        if(qcm.getQuestionList().size() > 20){
            QcmGlobalNote = QcmGlobalNote + 1;
            if(QcmGlobalNote > 5){
                QcmGlobalNote = Float.valueOf(5);
            }
        }

        return (float) Math.ceil(QcmGlobalNote * 2) / 2; // arrondi à 0.5
    }

    public ResponseEntity saveCandidateQcmHistory(Map<String, Object> candidateQcm){

        // todo: faire ça mieux, en mappant un vrai model...

        int totalWrong = 0;
        int totalQuestion = 0;
        int totalJoker = 0;

        // récupère le code
        String code = candidateQcm.get("code").toString();

        // Récupère le QcmHistory associé au code envoyé
        QcmHistory qcmHistory = qcmHistoryRepository.findOneByCode(code);

        // Si le test est noté "UNUSED", c'est que son "Employer" a annulé le passage du test (cancelQcm)
        if(qcmHistory.getStatus() == UNUSED){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le propriétaire de ce QCM a annulé le passage du test");
        }

        // Crée une liste des questions récupérées
        List<Question> questionList = qcmHistory.getQcm().getQuestionList();
        List<Map<Object, Object>> candidateQuestionList = (ArrayList) candidateQcm.get("questionList");

        List test = new ArrayList();
        int test2 = 0;

        // boucler sur les question récupérées
        for (Map<Object, Object> candidateQuestion : candidateQuestionList) {
            for(Question question : questionList) {



                if(question.getId().toString().equals(candidateQuestion.get("questionId").toString())) {
                    totalQuestion ++;
                    int candidateTotalAnswerTrueLength = 0;
                    int dbQuestionTotalAnswerTrueLength = 0;
                    boolean oneisWrong = false;

                    List<Integer> candidateAnswerList = (ArrayList) candidateQuestion.get("answerList");

                    if(candidateQuestion.get("timeOut") != null && candidateQuestion.get("timeOut").equals(true)){
                        question.setTotalNoTime(question.getTotalNoTime() +1);
                    }

                    if(candidateQuestion.get("joker") != null && candidateQuestion.get("joker").equals(true)){
                        totalJoker ++;
                        question.setTotalJoker(question.getTotalJoker() +1);

                    }else{ // si c'est pas un joker, on traîte la question :

                        // si une réponse est fausse :
                        for(Answer answer : question.getAnswerList()){
                            if(answer.getCorrect() == true){
                                dbQuestionTotalAnswerTrueLength ++;
                            }
                            for (Integer candidateAnswer : candidateAnswerList){
                                if(answer.getId().toString().equals(candidateAnswer.toString())){
                                    candidateTotalAnswerTrueLength ++;
                                    if(answer.getCorrect() == false){
                                        oneisWrong = true;
                                    }
                                }

                            }
                        }

                        // si toutes les bonnes réponses n'ont pas été cochées :
                        if(oneisWrong == false && candidateTotalAnswerTrueLength != dbQuestionTotalAnswerTrueLength){
                            oneisWrong = true;
                        }

                        if(oneisWrong){
                            totalWrong ++;
                            question.setTotalWrong(question.getTotalWrong() +1);
                            // populer totalright / totalWrong / totalTimeout sur la question
                        }else{
                            question.setTotalRight(question.getTotalRight() +1);
                        }
                    }
                }
            }
        }

        // recalculer la note globale du qcm et l'enregistrer en bdd
        qcmHistory.getQcm().setNote(SetQcmNote(qcmHistory.getQcm()));


        qcmHistory.setStatus(COMPLETE);
        qcmHistory.setSuccess(100 * ((totalQuestion-totalJoker) - totalWrong) / (totalQuestion-totalJoker));


        qcmHistoryRepository.save(qcmHistory);

        return ResponseEntity.status(HttpStatus.OK).body(qcmHistory);
    }



}
