package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.QcmHistoryStatus;
import com.checkskills.qcm.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

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

    private long purchased_credits;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Qcm getQcm() {
        return qcm;
    }

    public void setQcm(Qcm qcm) {
        this.qcm = qcm;
    }

    public Date getDateUsed() {
        return dateUsed;
    }

    public void setDateUsed(Date dateUsed) {
        this.dateUsed = dateUsed;
    }

    public Date getDateBought() {
        return dateBought;
    }

    public void setDateBought(Date dateBought) {
        this.dateBought = dateBought;
    }

    public long getPurchased_credits() {
        return purchased_credits;
    }

    public void setPurchased_credits(long purchased_credits) {
        this.purchased_credits = purchased_credits;
    }
}
