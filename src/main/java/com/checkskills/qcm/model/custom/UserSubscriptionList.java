package com.checkskills.qcm.model.custom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
public class UserSubscriptionList {
    private Long id;
    private Long plan_id;
    private Long employer_id;
    private Long credits_used;
    private Long plan_credits;
    public UserSubscriptionList(Long id, Long plan_id, Long employer_id, Long credits_used, Long plan_credits){
        this.id = id;
        this.plan_id = plan_id;
        this.employer_id = employer_id;
        this.credits_used = credits_used;
        this.plan_credits = plan_credits;
    }
}