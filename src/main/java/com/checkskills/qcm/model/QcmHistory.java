package com.checkskills.qcm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "qcm_history")
public class QcmHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "qcm_id")
    @JsonBackReference
    private Qcm qcm;

    @OneToOne
    @JoinColumn(name = "employer_id")
    private User employer;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int note;

    @Column(name = "date_used")
    private Date dateUsed;

    @Column(name = "date_bought")
    private Date dateBought;

    @Enumerated(EnumType.STRING)
    private QcmHistoryStatus status;


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
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
