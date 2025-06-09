package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.QcmHistoryStatus;
import com.checkskills.qcm.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "qcm_history")
public class QcmHistoryLite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "qcm_id")
    @JsonManagedReference
    private Qcm qcm;
    @Column(name = "date_used")
    private Date dateUsed;
    @Column(name = "date_bought")
    private Date dateBought;
    @Column(name = "date_invited")
    private Date dateInvited;
    private long purchased_credits;

}
