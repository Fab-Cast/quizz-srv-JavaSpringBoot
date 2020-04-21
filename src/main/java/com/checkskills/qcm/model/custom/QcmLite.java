package com.checkskills.qcm.model.custom;

public class QcmLite {

    private Long id;
    private String title;
    private int note;


    // Constructor

    public QcmLite(Long id, String title, int note) {
        this.id = id;
        this.title = title;
        this.note = note;
    }


    // Getters & Setters

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
}
