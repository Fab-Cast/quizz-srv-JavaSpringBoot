package com.checkskills.qcm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

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


    // getters & setters


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getTotalJoker() {
        return totalJoker;
    }

    public void setTotalJoker(int totalJoker) {
        this.totalJoker = totalJoker;
    }

    public int getTotalNoTime() {
        return totalNoTime;
    }

    public void setTotalNoTime(int totalNoTime) {
        this.totalNoTime = totalNoTime;
    }

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

    public int getTotalRight() {
        return totalRight;
    }

    public void setTotalRight(int totalTrue) {
        this.totalRight = totalTrue;
    }

    public int getTotalWrong() {
        return totalWrong;
    }

    public void setTotalWrong(int totalFalse) {
        this.totalWrong = totalFalse;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
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
