package com.checkskills.qcm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "qcm")
public class Qcm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String detail;
    private Float note;
    private Long credits;
    private boolean visible;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "qcm", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference // Résoud le pb infinite recursive (objets dupliqués à l'infini dans la réponse)
    private List<Question> questionList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "qcm_sector",
            joinColumns = @JoinColumn(name = "qcm_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id"))
    private List<Sector> sectorList;

    @Enumerated(EnumType.STRING)
    private QcmDifficulty difficulty;


    // getters & setters


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public QcmDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(QcmDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Long getCredits() { return credits; }

    public void setCredits(Long credits) { this.credits = credits; }

    public List<Sector> getSectorList() {
        return sectorList;
    }

    public void setSectorList(List<Sector> sectorList) {
        this.sectorList = sectorList;
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

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Float getNote() {
        return note;
    }

    public void setNote(Float note) {
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

}
