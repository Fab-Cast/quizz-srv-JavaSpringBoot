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

    public ResponseEntity purchaseQcm(Long qcmId, int qty, User user) {

        Qcm qcm = qcmService.getQcmById(qcmId, user);
        List<UserSubscriptionList> userSubscriptionList = subscriptionService.getUserSubscriptionList(user.getId());

        // si le QCM est gratuit
        if(qcm.getCredits() == 0){
            for (int i = 0; i < qty; i++){
                qcmHistoryService.savePurchasedQcm(qcm, user);
            }
            return ResponseEntity.status(HttpStatus.OK).body(qcmId);
        }

        // si l'utilisateur n'a jamais souscrit à un plan
        if(userSubscriptionList.size() == 0){
            return ResponseEntity.status(HttpStatus.LOCKED).body("insufficient credit");
        }

        // si l'utilisateur a plus de crédits que le coût du qcm (si il peut se le payer)
        Long toPay;
        toPay = qcm.getCredits()*qty;
        if(subscriptionRepository.totalCredit(user.getId()) >= toPay){

            // On débite les Sub
            for (int i = 0; i < userSubscriptionList.size(); i++) {

                System.out.println(userSubscriptionList.get(i).getCredits_used());

                UserSubscriptionList sub = userSubscriptionList.get(i);

                Long dispo = sub.getPlan_credits() - sub.getCredits_used(); // dispo = crédits dispo sur ce Sub

                Optional<Subscription> obtionalSubToSave = subscriptionRepository.findById(sub.getId());
                Subscription subToSave = obtionalSubToSave.get(); // récupération du Sub dans la bdd

                if(toPay>=dispo){ // Si le montant à payer mange tout le crédit du Sub, on le remplit et on le sauvegarde
                    subToSave.setCredits_used(sub.getPlan_credits());
                    subscriptionRepository.save(subToSave);
                    toPay = toPay-dispo;

                }else{
                    subToSave.setCredits_used(subToSave.getCredits_used() + toPay);
                    toPay = toPay-toPay;
                }

                System.out.println(userSubscriptionList.get(i).getCredits_used());
                System.out.println("---------------------");

            }

            // on génère les QCMHist
            for (int j = 0; j < qty; j++){
                qcmHistoryService.savePurchasedQcm(qcm, user);
            }

            return ResponseEntity.status(HttpStatus.OK).body(userSubscriptionList);

        }else{
            return ResponseEntity.status(HttpStatus.LOCKED).body("insufficient credit");
        }



    }


}
