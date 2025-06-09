package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.Qcm;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "question")
public class QuestionLite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int timeout;
    @ManyToOne
    @JoinColumn(name = "qcm_id")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Qcm qcm;
}
