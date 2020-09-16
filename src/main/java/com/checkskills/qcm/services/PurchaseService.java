package com.checkskills.qcm.services;

import com.checkskills.qcm.model.*;
import com.checkskills.qcm.model.custom.UserSubscriptionList;
import com.checkskills.qcm.repository.QcmHistoryRepository;
import com.checkskills.qcm.repository.SubscriptionRepository;
import com.checkskills.qcm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PurchaseService {

    @Autowired
    private QcmService qcmService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;


    @Autowired
    private QcmHistoryRepository qcmHistoryRepository;

    public ResponseEntity<?> purchaseQcm(Long qcmId, User user) {

        System.out.println("-------------------------------111");
        Qcm qcm = qcmService.getQcmById(qcmId, user);
        // vérifier que l'utilisateur ait assez de crédit

        //List<> monthlyCredit = subscriptionRepository.totalCredit(user.getId()) >= qcm.getPrice())
        System.out.println("-------------------------------222");
        // si l'utilisateur a plus de crédits que le coût du qcm (si il peut se le payer)
        if(subscriptionRepository.totalCredit(user.getId()) >= qcm.getCredits()){
            System.out.println("-------------------------------333");

            List<UserSubscriptionList> userSubscriptionList = subscriptionRepository.findAllUserSubscriptions(user.getId());
            int paid = 0;
            System.out.println("-------------------------------AAA");
            userSubscriptionList.forEach(sub ->{
                System.out.println("-------------------------------BBB");
                    if(sub.getCredits_used() > 0){
                        System.out.println("-------------------------------CCC");
                    }
                }
            );


            return ResponseEntity.status(HttpStatus.OK).body(userSubscriptionList);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body("T'as plus de tunes vieux, faut passer à la caisse");
        }


        /*
        // enregistrer le nouveau QCM_History avec un code unique
        QcmHistory qcmHistory = new QcmHistory();
        qcmHistory.setQcm(qcm);
        qcmHistory.setStatus(QcmHistoryStatus.UNUSED);
        qcmHistory.setDateBought(new Date());
        qcmHistory.setEmployer(user);

        qcmHistory.setCode(UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(qcmHistoryRepository.save(qcmHistory));

         */


    }


}
