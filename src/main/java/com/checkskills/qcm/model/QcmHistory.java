package com.checkskills.qcm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
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
    private Integer duration;
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
}
