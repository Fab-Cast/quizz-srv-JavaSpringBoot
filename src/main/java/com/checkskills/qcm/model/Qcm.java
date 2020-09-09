package com.checkskills.qcm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "qcm")
public class Qcm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int note;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "qcm", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference // Résoud le pb infinite recursive (objets dupliqués à l'infini dans la réponse)
    private List<Question> questionList;

    @OneToMany(mappedBy = "qcm", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<QcmHistory> qcmHistoryList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "qcm_sector",
            joinColumns = @JoinColumn(name = "qcm_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id"))
    private List<Sector> sectors;

    @Enumerated(EnumType.STRING)
    private QcmDifficulty difficulty;


// getters & setters


    public QcmDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(QcmDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Long getPrice() { return price; }

    public void setPrice(Long price) { this.price = price; }

    public List<Sector> getSectors() { return sectors; }

    public void setSectors(List<Sector> sectors) { this.sectors = sectors; }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<QcmHistory> getQcmHistoryList() {
        return qcmHistoryList;
    }

    public void setQcmHistoryList(List<QcmHistory> qcmHistoryList) {
        this.qcmHistoryList = qcmHistoryList;
    }
}
