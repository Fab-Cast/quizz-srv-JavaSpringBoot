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
import java.util.*;

@Service
public class PurchaseService {

    @Autowired
    private QcmService qcmService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private QcmHistoryService qcmHistoryService;


    @Autowired
    private QcmHistoryRepository qcmHistoryRepository;

    public ResponseEntity purchaseQcm(Long qcmId, User user) {

        Qcm qcm = qcmService.getQcmById(qcmId, user);

        // si l'utilisateur a plus de crédits que le coût du qcm (si il peut se le payer)
        if(subscriptionRepository.totalCredit(user.getId()) >= qcm.getCredits()){

            List<UserSubscriptionList> userSubscriptionList = subscriptionService.getUserSubscriptionList(user.getId());
            return ResponseEntity.status(HttpStatus.OK).body("AAAAAAAAAA");
            /*

            Long toPay;
            toPay = qcm.getCredits();


            for (int i = 0; i < userSubscriptionList.size(); i++) {
                UserSubscriptionList sub = userSubscriptionList.get(i);
                Long dispo = sub.getPlan_credits() - sub.getCredits_used();

                Optional<Subscription> obtionalSubToSave = subscriptionRepository.findById(sub.getId());
                Subscription subToSave = obtionalSubToSave.get();

                if(qcm.getCredits()<= dispo){
                    subToSave.setCredits_used(subToSave.getCredits_used() + toPay);
                    subscriptionRepository.save(subToSave);
                    break;
                }else{
                    toPay = qcm.getCredits() - (sub.getPlan_credits() - sub.getCredits_used());
                    subToSave.setCredits_used(sub.getPlan_credits()); // tout est consommé sur ce sub
                    subscriptionRepository.save(subToSave);
                }
            }

            qcmHistoryService.savePurchasedQcm(qcm, user);

            return ResponseEntity.status(HttpStatus.OK).body(userSubscriptionList);
             */
        }else{
            return ResponseEntity.status(HttpStatus.LOCKED).body("insufficient credit");
        }



    }


}
