package com.checkskills.qcm.repository;

import com.checkskills.qcm.model.Answer;
import com.checkskills.qcm.model.Plan;
import com.checkskills.qcm.model.Subscription;
import com.checkskills.qcm.model.custom.UserSubscriptionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // récupère le total des crédits dont l'utilisateur dispose
    @Query(value = "SELECT SUM(Plan.credits) - (SELECT SUM(credits_used) FROM Subscription WHERE employer_id = :employer_id) FROM Plan LEFT JOIN Subscription ON Plan.id = Subscription.plan_id WHERE (Subscription.employer_id = :employer_id)", nativeQuery = true)
    Long totalCredit(@Param("employer_id") Long employer_id);

    // récupère les "subscriptions" de l'utilisateur sur lesquelles il reste des credits, et les ordonne pour avoir les plans "monthly" en premier
    @Query(value = "SELECT subscription.id, subscription.plan_id, subscription.employer_id, subscription.credits_used, plan.credits as plan_credits FROM checkskills.subscription LEFT JOIN plan ON subscription.plan_id = plan.id WHERE employer_id = :employer_id AND (plan.credits - subscription.credits_used > 0) ORDER BY FIELD(plan.type, 'monthly', 'oneshot'), credits_used DESC", nativeQuery = true)
    List<UserSubscriptionList> findAllUserSubscriptions(@Param("employer_id") Long employer_id);


    /*
    public interface UserSubscriptionList {
        Long getId();
        Long getPlan_id();
        Long getEmployer_id();
        Long getCredits_used();
        Long getPlan_credits();
    }

     */


    List<Subscription> findByUserId(Long userId);

}
