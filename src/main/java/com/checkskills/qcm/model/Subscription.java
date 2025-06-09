package com.checkskills.qcm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
    @ManyToOne
    @JoinColumn(name = "employer_id")
    private User user;
    private Long credits_used;
    @Column(name = "date_bought")
    private Date dateBought;
}
