package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.*;
import com.checkskills.qcm.repository.PlanRepository;
import com.checkskills.qcm.repository.SubscriptionRepository;
import com.checkskills.qcm.repository.UserRepository;
import com.checkskills.qcm.services.StripeService;
import com.checkskills.qcm.services.SubscriptionService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    private StripeService stripeService;

    @Autowired
    SubscriptionController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @GetMapping("/subscription")
    public ResponseEntity getAllSubscription(){
        List<Subscription> subscriptionList = subscriptionService.findAllSubscription();
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionList);
    }

    @GetMapping("/subscription/current-auth")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity getCurrentAuthSubscriptionList(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        List<Subscription> subscriptionList = subscriptionRepository.findByUserIdOrderByDateBoughtDesc(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionList);
    }

    @PostMapping("/subscription/{planId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity saveSubscription(@RequestBody PaymentIntent paymentIntent, @PathVariable Long planId, Authentication authentication) throws StripeException {
        User user = userRepository.findByUsername(authentication.getName());
        return subscriptionService.saveSubscription(paymentIntent, planId, user);
    }

    @PostMapping("/checkout/{planId}")
    public ResponseEntity checkout(@PathVariable Long planId) throws Exception {
        return stripeService.createPayment(planId);
    }



}
