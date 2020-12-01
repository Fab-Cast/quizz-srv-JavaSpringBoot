package com.checkskills.qcm.model.custom;

import com.checkskills.qcm.model.Sector;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "qcm")
public class QcmLite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int note;
    private Long credits;
    private boolean visible;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserLite user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "qcm_sector",
            joinColumns = @JoinColumn(name = "qcm_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id"))
    private List<Sector> sectorList;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public UserLite getUser() {
        return user;
    }

    public void setUser(UserLite user) {
        this.user = user;
    }

    public List<Sector> getSectorList() {
        return sectorList;
    }

    public void setSectorList(List<Sector> sectorList) {
        this.sectorList = sectorList;
    }
}
