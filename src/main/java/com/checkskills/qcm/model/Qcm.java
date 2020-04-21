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

    @OneToMany(mappedBy = "qcm", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    //@OneToMany(cascade = {CascadeType.MERGE}) // Cascade : permet d'enregistrer les enfants (QuestionList et AnswerList) lors du SAVE de Qcm
    //@JoinColumn(name = "qcm_id") // Permet de lier les objets enfants dans le json retourné
    @JsonManagedReference // Résoud le pb infinite recursive (objets dupliqués à l'infini dans la réponse)
    private List<Question> questionList;

    // constructor




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

}
