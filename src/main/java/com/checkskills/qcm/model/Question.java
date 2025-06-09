package com.checkskills.qcm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int totalRight;
    private int totalWrong;
    private int timeout;
    private int totalNoTime;
    private int totalJoker;
    private String picture;
    @ManyToOne
    @JoinColumn(name = "qcm_id")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Qcm qcm;
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private List<Answer> answerList;
}
