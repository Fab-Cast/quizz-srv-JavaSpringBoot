package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.Plan;
import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.Subscription;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.repository.PlanRepository;
import com.checkskills.qcm.repository.SubscriptionRepository;
import com.checkskills.qcm.repository.UserRepository;
import com.checkskills.qcm.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PlanRepository planRepository;

    @GetMapping("/subscription")
    public ResponseEntity getAllSubscription(){
        List<Subscription> subscriptionList = subscriptionService.findAllSubscription();
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionList);
    }

    @GetMapping("/subscription/current-auth")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity getCurrentAuthSubscriptionList(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        List<Subscription> subscriptionList = subscriptionRepository.findByUserId(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionList);
    }

    @PostMapping("/subscription/{planId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity saveSubscription(@PathVariable Long planId, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        //Optional<Plan> plan = planService.findById(planId);

        Optional<Plan> plan = planRepository.findById(planId);

        Subscription sub = new Subscription();
        sub.setUser(user);
        sub.setPlan(plan.get());
        sub.setCredits_used(null);

        Subscription subscriptionSaved = subscriptionService.saveSubscription(user, sub);
        return ResponseEntity.status(HttpStatus.OK).body(sub);
    }
}
