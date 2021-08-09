package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.Qcm;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;


public class QcmHistoryTotalPurchased {

    private Long qcmId;
    private long total_purchased_credits;
    private long total_ordered;

    public Long getQcmId() {
        return qcmId;
    }

    public void setQcmId(Long qcmId) {
        this.qcmId = qcmId;
    }

    public long getTotal_purchased_credits() {
        return total_purchased_credits;
    }

    public void setTotal_purchased_credits(long total_purchased_credits) {
        this.total_purchased_credits = total_purchased_credits;
    }

    public long getTotal_ordered() {
        return total_ordered;
    }

    public void setTotal_ordered(long total_ordered) {
        this.total_ordered = total_ordered;
    }
}
