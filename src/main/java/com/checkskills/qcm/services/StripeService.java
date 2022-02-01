package com.checkskills.qcm.services;

import com.checkskills.qcm.model.Plan;
import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.Role;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.model.custom.QcmHistoryTotalPurchased;
import com.checkskills.qcm.repository.PlanRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StripeService {

    @Autowired
    private PlanRepository planRepository;

    @Value("${stripeApiKey}")
    private String stripeApiKey;


    /*
    @Autowired
    StripeService() {
        Stripe.apiKey = "sk_test_51JS8mEC3d28wKFxAM3MTNztevTDXqpwmdvsLOCiuyizNPbzEea1FvN7oLgCJ2rPRsJ8bN2m0Rr4s31Ia03Tm9Ehj00E0YmGudL";
    }

    public Charge chargeCreditCard(String token, double amount) throws Exception {
        System.out.println("22222222222222222");
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        System.out.println("3333333333333333333");
        chargeParams.put("amount", (int)(amount * 100));
        System.out.println("4444444444444444");
        chargeParams.put("currency", "USD");
        System.out.println("55555555555555555");
        chargeParams.put("source", token);
        System.out.println("6666666666666666666");
        Charge charge = Charge.create(chargeParams);
        System.out.println("77777777777777");
        return charge;
    }
    */

    public ResponseEntity createPayment(Long planId, User user) throws StripeException {


        if(!user.getRoles().stream().findFirst().get().getName().toString().equals("ROLE_EMPLOYER")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user.getRoles().stream().findFirst());
        }

        Stripe.apiKey = stripeApiKey;
        Optional<Plan> plan = planRepository.findById(planId);

        if(plan.isPresent()){

            List<Object> paymentMethodTypes = new ArrayList<>();
            paymentMethodTypes.add("card");
            Map<String, Object> params = new HashMap<>();
            params.put("amount", plan.get().getPrice()*100);
            params.put("currency", "eur");
            params.put("payment_method_types",paymentMethodTypes);

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            Map<String, Object> result = new HashMap<String,Object>();
            result.put("secret",paymentIntent.getClientSecret());

            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


    }

    @Async
    public Boolean hasPaid(PaymentIntent paymentIntent) throws StripeException {

        PaymentIntent paymentIntentStripe = PaymentIntent.retrieve(paymentIntent.getId());
        if (paymentIntentStripe.getStatus().equals("succeeded")) {
            System.out.println("------------------------------------------true");
            return true;
        } else{
            System.out.println("------------------------------------------false");
            return false;
        }

        /*
        try {

        } catch (Exception e) {
            System.out.println("------------------------------------------paymentIntentStripe.getStatus() --- e");
            System.out.println(e);
            return false;
        }

         */


    }

}
