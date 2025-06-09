package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.Qcm;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
public class QcmHistoryTotalPurchased {
    private Long qcmId;
    private long total_purchased_credits;
    private long total_ordered;
}
