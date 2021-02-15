package com.checkskills.qcm.services;

import com.checkskills.qcm.model.Plan;
import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.Subscription;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.model.custom.UserSubscriptionList;
import com.checkskills.qcm.repository.PlanRepository;
import com.checkskills.qcm.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
