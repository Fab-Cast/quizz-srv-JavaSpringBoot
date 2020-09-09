package com.checkskills.qcm.services;

import com.checkskills.qcm.model.Plan;
import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.Subscription;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.repository.PlanRepository;
import com.checkskills.qcm.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> findAllSubscription() {
        return subscriptionRepository.findAll();
    }

    public Subscription saveSubscription(User user, Subscription subscription) {
        subscription.setUser(user);
        return subscriptionRepository.save(subscription);
    }
}
