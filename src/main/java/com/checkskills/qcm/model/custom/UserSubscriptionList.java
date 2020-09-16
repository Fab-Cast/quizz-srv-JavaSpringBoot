package com.checkskills.qcm.model.custom;

public class UserSubscriptionList {

    private Long id;
    private Long plan_id;
    private Long employer_id;
    private Long credits_used;
    private Long plan_credits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(Long plan_id) {
        this.plan_id = plan_id;
    }

    public Long getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(Long employer_id) {
        this.employer_id = employer_id;
    }

    public Long getCredits_used() {
        return credits_used;
    }

    public void setCredits_used(Long credits_used) {
        this.credits_used = credits_used;
    }

    public Long getPlan_credits() {
        return plan_credits;
    }

    public void setPlan_credits(Long plan_credits) {
        this.plan_credits = plan_credits;
    }
}