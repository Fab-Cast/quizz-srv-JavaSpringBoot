package com.checkskills.qcm.services;

import com.checkskills.qcm.model.Plan;
import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.Subscription;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.model.custom.UserSubscriptionList;
import com.checkskills.qcm.repository.PlanRepository;
import com.checkskills.qcm.repository.SubscriptionRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private StripeService stripeService;

    public List<Subscription> findAllSubscription() {
        return subscriptionRepository.findAll();
    }

    public ResponseEntity saveSubscription(PaymentIntent paymentIntent, Long planId, User user) throws StripeException {


        if(stripeService.hasPaid(paymentIntent)){
            Optional<Plan> plan = planRepository.findById(planId);
            Subscription sub = new Subscription();
            sub.setUser(user);
            sub.setPlan(plan.get());
            sub.setCredits_used((long) 0);
            sub.setDateBought(new Date());
            Subscription subscriptionSaved = subscriptionRepository.save(sub);
            return ResponseEntity.status(HttpStatus.OK).body(subscriptionSaved);
        }else{
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(null);
        }

    }

    public List<UserSubscriptionList> getUserSubscriptionList(Long userId) {

        List<UserSubscriptionList> userSubscriptionList = subscriptionRepository.findAllUserSubscriptions(userId);

        return userSubscriptionList;

        /*
        List<UserSubscriptionList> userSubscriptionList = subscriptionRepository.findAllUserSubscriptions(userId).stream().map(e -> {

            UserSubscriptionList dto = new UserSubscriptionList();

            dto.setId(e.getId());
            dto.setPlan_id(e.getPlan_id());
            dto.setEmployer_id(e.getEmployer_id());
            dto.setCredits_used(e.getCredits_used());
            dto.setPlan_credits(e.getPlan_credits());

            return dto;
        }).collect(Collectors.toList());

        return userSubscriptionList;

         */

    }
}
