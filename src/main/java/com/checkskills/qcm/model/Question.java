package com.checkskills.qcm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int note;
    private int totalTrue;
    private int totalFalse;
    private int timeout;
    private Boolean visible;

    @ManyToOne
    @JoinColumn(name = "qcm_id")
    @JsonBackReference
    private Qcm qcm;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    //@OneToMany(cascade = {CascadeType.MERGE})
    //@JoinColumn(name = "question_id")
    @JsonManagedReference
    private List<Answer> answerList;


    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getTotalTrue() {
        return totalTrue;
    }

    public void setTotalTrue(int totalTrue) {
        this.totalTrue = totalTrue;
    }

    public int getTotalFalse() {
        return totalFalse;
    }

    public void setTotalFalse(int totalFalse) {
        this.totalFalse = totalFalse;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Qcm getQcm() {
        return qcm;
    }

    public void setQcm(Qcm qcm) {
        this.qcm = qcm;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
