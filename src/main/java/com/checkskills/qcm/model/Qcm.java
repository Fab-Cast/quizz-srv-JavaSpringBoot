package com.checkskills.qcm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "qcm")
public class Qcm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //@JsonBackReference
    //@JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "qcm", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference // Résoud le pb infinite recursive (objets dupliqués à l'infini dans la réponse)
    private List<Question> questionList;




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

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) { this.questionList = questionList; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
