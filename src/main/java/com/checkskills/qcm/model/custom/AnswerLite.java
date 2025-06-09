package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.Question;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "answer")
public class AnswerLite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;
}
