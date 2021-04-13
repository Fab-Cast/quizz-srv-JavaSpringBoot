package com.checkskills.qcm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "qcm_history")
public class QcmHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "qcm_id")
    @JsonManagedReference
    private Qcm qcm;

    @OneToOne
    @JoinColumn(name = "employer_id")
    private User employer;

    private int success;

    @Column(name = "date_used")
    private Date dateUsed;

    @Column(name = "date_invited")
    private Date dateInvited;

    @Column(name = "date_bought")
    private Date dateBought;

    @Enumerated(EnumType.STRING)
    private QcmHistoryStatus status;

    private String code;

    private String candidate_name;

    private String candidate_mail;

    private long purchased_credits;


    public String getCandidate_mail() {
        return candidate_mail;
    }

    public void setCandidate_mail(String candidate_mail) {
        this.candidate_mail = candidate_mail;
    }

    public Date getDateInvited() {
        return dateInvited;
    }

    public void setDateInvited(Date dateInvited) {
        this.dateInvited = dateInvited;
    }

    public String getCandidate_name() {
        return candidate_name;
    }

    public void setCandidate_name(String candidate_name) {
        this.candidate_name = candidate_name;
    }

    public long getPurchased_credits() {
        return purchased_credits;
    }

    public void setPurchased_credits(long purchased_credits) {
        this.purchased_credits = purchased_credits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public QcmHistoryStatus getStatus() {
        return status;
    }

    public void setStatus(QcmHistoryStatus status) {
        this.status = status;
    }

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

    public User getEmployer() {
        return employer;
    }

    public void setEmployer(User employer) {
        this.employer = employer;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
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

}
